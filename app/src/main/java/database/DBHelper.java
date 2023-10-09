package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.model.UsuarioModel;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "cheaptrip.db";
    private static final int VERSAO_BANCO = 1;

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioModel.DROP_TABLE);
        db.execSQL(UsuarioModel.CREATE_TABLE);
    }
}
