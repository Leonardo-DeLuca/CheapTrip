package database.dao;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import database.DBHelper;

public abstract class Base {
    protected SQLiteDatabase db;
    protected DBHelper dbHelper;

    protected final void Open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    protected final void Close() throws SQLException {
        dbHelper.close();
    }
}
