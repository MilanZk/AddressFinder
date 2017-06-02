package com.example.praktikant.addressfinder;


import java.sql.SQLException;

public class AddressFinderException extends Exception {

    public AddressFinderException(SQLException sqlException) {

    }

    public AddressFinderException(String message) {
        super(message);
    }

    public AddressFinderException(Throwable cause) {
        super(cause);
    }

    public AddressFinderException(String message, Throwable cause) {
        super(message, cause);
    }
}
