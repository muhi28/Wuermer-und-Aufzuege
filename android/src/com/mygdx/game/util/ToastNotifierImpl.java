package com.mygdx.game.util;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastNotifierImpl implements ToastNotifier {

    private final Context appContext;

    public ToastNotifierImpl(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public void showToast(final String toastMsg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(appContext, toastMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
