package com.example.praktikant.addressfinder.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = Bookmark.TABLE_NAME_BOOKMARK)
public class Bookmark implements Serializable {

    /*Properties*/

    public static final String TABLE_NAME_BOOKMARK = "bookmark";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_ADDRESS = "address";
    public static final String FIELD_NAME_CITY = "city";
    public static final String FIELD_NAME_STATE = "state";
    public static final String FIELD_NAME_POSTAL = "postal";
    public static final String FIELD_NAME_LATITUDE = "latitude";
    public static final String FIELD_NAME_LONGITUDE = "longitude";
    public static final String DATA_TYPE_JSON = "pjson";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = FIELD_NAME_ADDRESS)
    private String address;
    @DatabaseField(columnName = FIELD_NAME_CITY)
    private String city;
    @DatabaseField(columnName = FIELD_NAME_STATE)
    private String state;
    @DatabaseField(columnName = FIELD_NAME_POSTAL)
    private String postal;
    @DatabaseField(columnName = FIELD_NAME_LATITUDE)
    private double latitude;
    @DatabaseField(columnName = FIELD_NAME_LONGITUDE)
    private double longitude;

    public Bookmark() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        String sb = "Position{" + "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postal='" + postal + '\'' +
                '}';
        return sb;
    }
}
