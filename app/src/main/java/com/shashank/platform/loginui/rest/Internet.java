package com.shashank.platform.loginui.rest;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Internet {
    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public static boolean isConnected(@NonNull Context context) {
        return (
                (ConnectivityManager) Objects.requireNonNull(
                        context.getSystemService(Context.CONNECTIVITY_SERVICE)
                )
        ).getActiveNetworkInfo() != null;
    }
}
