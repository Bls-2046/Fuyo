package com.github.fuyo.listener;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.UserEntity;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.fuyo.view.navigation.schedule.ScheduleDialogView.showDialog;

public class ScheduleListener {

    public ScheduleListener() {
        monitorAndTrigger(0);
        monitorAndTrigger(1);
        monitorAndTrigger(2);
    }

    // 时间监听器
    private void monitorAndTrigger(int idx) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // 1S检查一次时间
        executor.scheduleAtFixedRate(() -> {

            List<ScheduleEntity> schedule = UserEntity.getUserInformation().getSchedule();
            ScheduleEntity scheduleEntity = schedule.get(idx);

            if (scheduleEntity != null) {

                LocalDateTime now = LocalDateTime.now();

                // 如果当前时间位于Reminder和Date之间
                if ((now.isAfter(scheduleEntity.getReminderDatetime()) && now.isBefore(scheduleEntity.getDatetime()))) {

                    SwingUtilities.invokeLater(() -> {

                        // TODO: 服务端删除


                        // 显示提示框
                        showDialog(scheduleEntity);

                    });

                }

            }

        }, 0, 1000, TimeUnit.MILLISECONDS); // 1 SEC / CHECK
    }

}
