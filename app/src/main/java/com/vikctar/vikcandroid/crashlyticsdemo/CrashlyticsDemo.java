package com.vikctar.vikcandroid.crashlyticsdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CrashlyticsDemo extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                handleUncaughtException(thread, throwable);
                createLogFile(thread, throwable);
            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable throwable) {
        String stackTrace = Log.getStackTraceString(throwable);
        String message = throwable.getMessage();
        String mailto = "mailto:mmehari@pathwaysinternational.com" + "?cc=" + "victor@pathwaysinternantional.com";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(mailto));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ivar CrashReport");
        intent.putExtra(Intent.EXTRA_TEXT, message +"\n" +"******************\n\n" + stackTrace);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void createLogFile(Thread thread, Throwable throwable) {
        String logger = "crash_log.txt";
        String stackTrace = Log.getStackTraceString(throwable);
        String message = throwable.getMessage();

        try {
            FileOutputStream fileOutputStream = openFileOutput(logger, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(message +"\n" +"******************\n\n" + stackTrace);
            Log.d("Log", "Log file created");
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
