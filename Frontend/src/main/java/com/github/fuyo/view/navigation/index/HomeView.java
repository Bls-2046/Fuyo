package com.github.fuyo.view.navigation.index;

import com.github.fuyo.dto.thirdPartyAPI.FetchWeatherResponse;
import com.github.fuyo.model.HomeModel;
import com.github.fuyo.utils.layout.RUILabel;
import com.github.fuyo.view.messagebox.ErrorMessageBox;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class HomeView extends JLayeredPane {

    public static enum Time {
        MORNING,
        NIGHT,
    }

    private String sentence;
    private FetchWeatherResponse weather;

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

        // YiYan
        try {
            sentence = HomeModel.fetchYiYan();
            while(sentence.length() > 30) {
                // YiYan ReAcquired
                try {
                    sentence = HomeModel.fetchYiYan();
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessageBox.showErrorBox("YiYan API Fetch Failed");
        }

        // Weather
        FetchWeatherResponse.Live live = null;
        try {
            weather = HomeModel.fetchWeather();
            live = Objects.requireNonNull(weather).getLive();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessageBox.showErrorBox("Amap Weather API Fetch Failed");
        }

        // Render Shadow
        add(RUILabel.getCenterEmptyTextLabel(49,537,1000,55,sentence,30,"青鸟华光行草", Color.WHITE,Font.BOLD),POPUP_LAYER);
        add(RUILabel.getCenterEmptyTextLabel(52,540,1000,55,sentence,30,"青鸟华光行草",new Color(203,203,203),Font.BOLD),POPUP_LAYER);

        add(RUILabel.getCenterEmptyTextLabel(62,357,977,54,
                date.getYear() + "." + date.getMonthValue() + "." + date.getDayOfMonth() + "  "
                + ((live == null) ? "天气数据获取失败" : live.getCity() + " " + live.getWeather() + " " + live.getTemperature() + "°C")
                ,22,"微软雅黑", (time == Time.NIGHT) ? Color.LIGHT_GRAY : Color.GRAY,Font.PLAIN),POPUP_LAYER);


        setVisible(true);
    }
}
