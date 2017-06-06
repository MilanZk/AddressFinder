package com.example.praktikant.addressfinder.exceptions;


import java.sql.SQLException;

public class AddressFinderException extends Exception {

    public AddressFinderException(SQLException e) {
        super(e);
    }
}
