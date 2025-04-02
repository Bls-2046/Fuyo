package com.github.backend.service.impl.Impls;

import com.github.dto.tabletime.FetchTabletimeRequest;
import com.github.dto.tabletime.FetchTabletimeResponse;
import com.github.backend.entity.mysql.TabletimeEntity;
import com.github.backend.repository.mysql.TabletimeRepository;
import com.github.backend.service.TabletimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class TabletimeServiceImpl implements TabletimeService {
    private final TabletimeRepository tabletimeRepository;

    @Autowired
    public TabletimeServiceImpl(TabletimeRepository tabletimeRepository) {
        this.tabletimeRepository = tabletimeRepository;
    }

    /**
     * 获取用户当天课表信息
     * @param fetchTabletimeRequest 用户名用于查询
     * @return 返回当天课表
     */
    @Override
    public List<FetchTabletimeResponse.Tabletime> fetchTabletime(FetchTabletimeRequest fetchTabletimeRequest) {
        String username = fetchTabletimeRequest.getUsername();

        // 计算是第几周的第几天
        LocalDate firstDay = LocalDate.of(2025, 2, 23);
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 计算从第一周到今天的天数
        long daysBetween = ChronoUnit.DAYS.between(firstDay, today);
        // 计算当前是第几周（向上取整）

        int currentWeek = (int) Math.ceil((double) daysBetween / 7);
        // 如果为双周
        String weekType = (currentWeek % 2 == 0 ? "双周" : "单周");
        // 计算当前是第几周的第几天（1到7）
        int dayOfWeek = (int) (daysBetween % 7 == 0 ?  7 : daysBetween % 7);

        List<TabletimeEntity> queryTabletime = tabletimeRepository.findByUserEntityUsernameAndX(username, dayOfWeek);

        // 创建 tabletime 列表
        List<FetchTabletimeResponse.Tabletime> tabletimeList = new ArrayList<>();

        // 遍历 queryTabletime, 将每个对象转换为 TabletimeResponse.Tabletime
        for (TabletimeEntity tabletime : queryTabletime) {
            FetchTabletimeResponse.Tabletime tabletimeResponseTabletime = new FetchTabletimeResponse.Tabletime();
            if (tabletime.getWeekType() != null) {
                if (!tabletime.getWeekType().equals(weekType)) {
                    continue;
                }
            }
            if (tabletime.getStartWeek() <= currentWeek && tabletime.getFinishWeek() >= currentWeek) {

                tabletimeResponseTabletime.setKeyID(tabletime.getId())
                                            .setClazz(tabletime.getClazz())
                                            .setX(tabletime.getX())
                                            .setY(tabletime.getY())
                                            .setBeginDay(tabletime.getBeginDay())
                                            .setEndDay(tabletime.getEndDay())
                                            .setWeekType(tabletime.getWeekType())
                                            .setPlace(tabletime.getPlace())
                                            .setStartWeek(tabletime.getStartWeek())
                                            .setFinishWeek(tabletime.getFinishWeek());

                // 添加到 tabletime 列表
                tabletimeList.add(tabletimeResponseTabletime);
            }
        }
        tabletimeList.sort(Comparator.comparingInt(FetchTabletimeResponse.Tabletime::getY));

        log.info(tabletimeList.toString());

        return tabletimeList;
    }
}
