package com.github.fuyo.model;

import com.github.fuyo.dto.TableTimeResponse;
import com.github.fuyo.dto.TabletimeRequest;
import com.github.fuyo.entity.TabletimeEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.HttpHeaders;
import com.github.fuyo.utils.https.Https;
import com.github.fuyo.utils.https.HttpsTest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TabletimeModel {
    public TabletimeModel() {}

    public static void fetchTabletime(String username) {
        String url = "http://localhost:8080/api/fetch/tabletime";
        TabletimeRequest tabletimeRequest = new TabletimeRequest();
        tabletimeRequest.setUsername(username);

        try {
            TableTimeResponse tableTimeResponse = HttpsTest.<TableTimeResponse>post(url, tabletimeRequest, HttpHeaders.basic(), TableTimeResponse.class);

            List<TabletimeEntity> tabletimeEntity = tableTimeResponse.getTabletime().stream()
                    .map(responseTabletime -> new TabletimeEntity(
                            responseTabletime.getKeyID(),
                            responseTabletime.getClazz(),
                            responseTabletime.getX(),
                            responseTabletime.getY(),
                            responseTabletime.getBeginDay(),
                            responseTabletime.getEndDay(),
                            responseTabletime.getWeekType(),
                            responseTabletime.getPlace(),
                            responseTabletime.getStartWeek(),
                            responseTabletime.getFinishWeek()
                    ))
                    .collect(Collectors.toList());

            log.info("成功获取用户课表信息: {}", tabletimeEntity);

            // 使用同步块确保线程安全
            synchronized (UserEntity.getUserInformation()) {
                UserEntity.getUserInformation().setTabletimeEntity(tabletimeEntity);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
