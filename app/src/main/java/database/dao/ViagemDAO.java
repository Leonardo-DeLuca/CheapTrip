package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import database.DBHelper;
import database.model.UsuarioModel;
import database.model.ViagemModel;

public class ViagemDAO extends Base {

    public ViagemDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(ViagemModel viagemModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_ID_USUARIO, viagemModel.getIdUsuario());
            values.put(ViagemModel.COLUNA_TITULO, viagemModel.getTitulo());
            values.put(ViagemModel.COLUNA_TOTAL_VIAJANTES, viagemModel.getTotalViajantes());
            values.put(ViagemModel.COLUNA_DURACAO, viagemModel.getDuracao());
            values.put(ViagemModel.COLUNA_DATA_CRIACAO, viagemModel.getDataCriacao());
            values.put(ViagemModel.COLUNA_TOTAL, viagemModel.getTotal());

            linhasAfetadas = db.insert(ViagemModel.TABELA, null, values);
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public List<ViagemModel> selectBy(String colunaParametro, String valorParametro) {
        List<ViagemModel> viagensRetornadas = new ArrayList<>();
        String[] params = { valorParametro };

        try {
            Open();

            Cursor cursor = db.query(ViagemModel.TABELA, ViagemModel.getColunas(), colunaParametro + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    viagensRetornadas.add(CursorParaViagem(cursor));
                    cursor.moveToNext();
                }
            }

            cursor.close();
        }
        finally {
            Close();
        }

        return viagensRetornadas.isEmpty() ? null : viagensRetornadas;
    }

    private ViagemModel CursorParaViagem(Cursor cursor) {
        ViagemModel v = new ViagemModel();

        v.setId(cursor.getInt(0));
        v.setIdUsuario(cursor.getInt(1));
        v.setTitulo(cursor.getString(2));
        v.setTotalViajantes(cursor.getInt(3));
        v.setDuracao(cursor.getInt(4));
        v.setDataCriacao(cursor.getInt(5));
        v.setTotal(cursor.getDouble(6));

        return v;
    }
}
