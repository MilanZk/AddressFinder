package com.example.praktikant.addressfinder.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Praktikant on 22.5.2017..
 */
@DatabaseTable(tableName = Bookmark.TABLE_NAME_POSITION)
public class Bookmark {

    /*Properties*/

    public static final String TABLE_NAME_POSITION = "position";
    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_ADDRESS   = "address";
    public static final String FIELD_NAME_CITY    = "city";
    public static final String FIELD_NAME_STATE    = "state";
    public static final String FIELD_NAME_POSTAL    = "postal";


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

    public Bookmark(int id, String address, String city, String state, String postal) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal = postal;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Position{");
        sb.append("id=").append(id);
        sb.append(", address='").append(address).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", postal='").append(postal).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
