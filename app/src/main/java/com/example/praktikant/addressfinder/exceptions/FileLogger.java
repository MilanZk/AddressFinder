package com.example.praktikant.addressfinder.exceptions;


import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FileLogger {

    private File file;

    public void logIntoFile(Context context, Exception exception) {
        openFile(context);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Arrays.toString(exception.getStackTrace()));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File openFile(Context context) {
        return file = new File(context.getFilesDir(), "logFile.txt");
    }

}

