package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.model.EntretenimentoModel;
import database.model.GasolinaModel;
import database.model.HospedagemModel;
import database.model.RefeicoesModel;
import database.model.TarifaAereaModel;
import database.model.UsuarioModel;
import database.model.ViagemModel;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "cheaptrip.db";
    private static final int VERSAO_BANCO = 1;

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioModel.CREATE_TABLE);
        db.execSQL(ViagemModel.CREATE_TABLE);
        db.execSQL(GasolinaModel.CREATE_TABLE);
        db.execSQL(TarifaAereaModel.CREATE_TABLE);
        db.execSQL(RefeicoesModel.CREATE_TABLE);
        db.execSQL(HospedagemModel.CREATE_TABLE);
        db.execSQL(EntretenimentoModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioModel.DROP_TABLE);
        db.execSQL(UsuarioModel.CREATE_TABLE);
        db.execSQL(ViagemModel.DROP_TABLE);
        db.execSQL(ViagemModel.CREATE_TABLE);
        db.execSQL(GasolinaModel.DROP_TABLE);
        db.execSQL(GasolinaModel.CREATE_TABLE);
        db.execSQL(TarifaAereaModel.DROP_TABLE);
        db.execSQL(TarifaAereaModel.CREATE_TABLE);
        db.execSQL(RefeicoesModel.DROP_TABLE);
        db.execSQL(RefeicoesModel.CREATE_TABLE);
        db.execSQL(HospedagemModel.CREATE_TABLE);
        db.execSQL(HospedagemModel.DROP_TABLE);
        db.execSQL(EntretenimentoModel.CREATE_TABLE);
        db.execSQL(EntretenimentoModel.DROP_TABLE);
    }
}
