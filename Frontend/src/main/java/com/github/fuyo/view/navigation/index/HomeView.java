package com.github.fuyo.view.navigation.index;

import javax.swing.*;

public class HomeView extends JLayeredPane {

    public static enum Time {
        MORNING,
        NIGHT,
    }

    public HomeView(Time time) {

        // Fixed
        setBounds(260, 0, 1100, 768);




        setVisible(true);
    }
}
