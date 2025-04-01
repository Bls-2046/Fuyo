package com.github.fuyo.view.navigation.index;

import com.github.fuyo.dto.thirdPartyAPI.FetchWeatherResponse;
import com.github.fuyo.model.HomeModel;
import com.github.fuyo.utils.layout.RUILabel;
import com.github.fuyo.view.messagebox.ErrorMessageBox;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Slf4j
public class HomeView extends JLayeredPane {

    public static enum Time {
        MORNING,
        NIGHT,
    }

    public HomeView() {

        // Get Current Time
        HomeView.Time time = HomeView.Time.MORNING;
        LocalDate date = LocalDate.now();

        if ( LocalTime.now().isAfter(LocalTime.of(18,0)) ||
                LocalTime.now().isBefore(LocalTime.of(6,0)) ) {
            time = Time.NIGHT;
        }

        // Fixed
        setBounds(260, 0, 1100, 768);

        RUILabel bg = new RUILabel("mainFrame", (time == Time.NIGHT) ? "indexLayer.png" : "indexLayerDay.png");
        add(bg.imageLabel(0,0), DEFAULT_LAYER);

        int retryCount = 0;

        //YiYan
        String sentence = "";
        try {
            sentence = HomeModel.fetchYiYan();
            log.info(sentence);
            while(sentence.length() > 30 && !sentence.isEmpty() && retryCount < 5) {
                // YiYan ReAcquired
                try {
                    sentence = HomeModel.fetchYiYan();
                    Thread.sleep(500);
                    retryCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (sentence.isEmpty()) throw new Exception("YiYan API Re-Fetch Retry Count Reached Maximum(5)");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessageBox.showErrorBox("YiYan API Fetch Failed");
        }

        // Weather
        FetchWeatherResponse.Live live = null;
        try {
            log.info("Weather: {}", HomeModel.fetchWeather());
            live = Objects.requireNonNull(HomeModel.fetchWeather().getLive());
        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessageBox.showErrorBox("Amap Weather API Fetch Failed");
        }

        // Render Shadow
        add(RUILabel.getCenterEmptyTextLabel(49,487,1000,55, sentence,35,"青鸟华光行草", Color.WHITE,Font.BOLD),POPUP_LAYER);
        add(RUILabel.getCenterEmptyTextLabel(51,489,1000,55, sentence,35,"青鸟华光行草",new Color(203,203,203),Font.BOLD),POPUP_LAYER);

        add(RUILabel.getCenterEmptyTextLabel(62,357,977,54,
                date.getMonthValue() + "月" + date.getDayOfMonth() + "日  "
                + ((live == null) ? "天气数据获取失败" : live.getCity() + " " + live.getWeather() + " " + live.getTemperature() + "°C")
                ,22,"微软雅黑", Color.LIGHT_GRAY ,Font.PLAIN),POPUP_LAYER);


        setVisible(true);
    }
}
