package com.example.attendancesystem_client_android;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ToastChildThread {
    public static void show(Context context, CharSequence text, int duration){
        Looper.prepare();
        Toast.makeText(context, text, duration).show();
        Looper.loop();
    }
}
