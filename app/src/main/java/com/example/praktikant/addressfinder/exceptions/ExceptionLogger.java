package com.example.praktikant.addressfinder.exceptions;


import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ExceptionLogger {

    public static void logIntoFile(Context context, Exception exception) {
        File logFile = new File(context.getFilesDir().getAbsolutePath());
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(Arrays.toString(exception.getStackTrace()));
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
