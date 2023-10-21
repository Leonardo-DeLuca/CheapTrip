package database.dao;

import android.content.Context;

import database.DBHelper;

public class ViagemDAO extends Base {

    public ViagemDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert() {
        return 0;
    }
}
