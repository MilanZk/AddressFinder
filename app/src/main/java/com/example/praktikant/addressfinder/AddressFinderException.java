package com.example.praktikant.addressfinder;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class AddressFinderException extends Exception {

    public AddressFinderException(SQLException e) {
        logIntoFile(Arrays.toString(e.getStackTrace()));
    }

    private void logIntoFile(String text) {
        File logFile = new File("sdcard/log.file");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
