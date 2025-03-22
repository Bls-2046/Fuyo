package com.github.fuyo.utils;

import lombok.Getter;

public class ShareLock {
    @Getter
    private final Object lock = new Object();
    private boolean isUserLoadingThreadDone = false;

    public boolean isUserLoadingThreadDone() {
        return isUserLoadingThreadDone;
    }

    public void setUserLoadingThreadDone(boolean userLoadingThreadDone) {
        isUserLoadingThreadDone = userLoadingThreadDone;
    }
}
