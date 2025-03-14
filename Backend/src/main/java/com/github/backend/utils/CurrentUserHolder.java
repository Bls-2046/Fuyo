package com.github.backend.utils;

import com.github.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserHolder {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
