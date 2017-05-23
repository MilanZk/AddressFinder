package com.example.praktikant.addressfinder.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.praktikant.addressfinder.model.Position;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;



public class ORMDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME    = "position.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Position, Integer> mPositionDao = null;


    public ORMDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Position.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Position.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Dao<Position, Integer> getPositionDao() throws SQLException {
        if (mPositionDao == null) {
            mPositionDao= getDao(Position.class);
        }

        return mPositionDao;
    }


    //release resources
    @Override
    public void close() {
        mPositionDao = null;


        super.close();
    }
}