package com.github.fuyo.view.navigation.user;

import com.github.fuyo.entity.UserEntity;

import javax.swing.*;

public class UserView extends JLayeredPane {

    private UserEntity user;

    public UserView(UserEntity user) {
        this.user = user;

        setBounds(260, 0, 1100, 768);



    }

}
