package com.github.backend.utils;

import com.github.backend.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserHolder {
    private UserEntity currentUserEntity;

    public UserEntity getCurrentUser() {
        return currentUserEntity;
    }

    public void setCurrentUser(UserEntity currentUserEntity) {
        this.currentUserEntity = currentUserEntity;
    }
}
