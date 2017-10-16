package com.vbazh.weatherapp_mvp.utils;


import android.support.annotation.NonNull;

public class BaseUtils {

    public static @NonNull
    <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static boolean isNullOrEmpty(String string) {

        return string == null || string.length() == 0;

    }

}
