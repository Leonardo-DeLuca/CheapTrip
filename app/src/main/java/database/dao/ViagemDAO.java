package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import database.DBHelper;
import database.model.GasolinaModel;
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

            linhasAfetadas = db.insert(ViagemModel.TABELA, null, getContentValues(viagemModel));
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

        return viagensRetornadas;
    }

    public ViagemModel selectById(String idViagem) {
        ViagemModel viagemRetornada = null;
        String[] params = { idViagem };

        try {
            Open();

            Cursor cursor = db.query(ViagemModel.TABELA, ViagemModel.getColunas(), ViagemModel.COLUNA_ID + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                viagemRetornada = CursorParaViagem(cursor);
                cursor.close();
            }
        }
        finally {
            Close();
        }

        return viagemRetornada;
    }

    public void update(ViagemModel viagemModel) {
        String[] params = { String.valueOf(viagemModel.getId()) };
        ContentValues values = getContentValues(viagemModel);

        values.put(ViagemModel.COLUNA_ID, viagemModel.getId());

        try {
            Open();

            db.update(ViagemModel.TABELA, values, ViagemModel.COLUNA_ID + " = ?", params);
        }
        finally {
            Close();
        }
    }

    public void deleteBy(String colunaParametro, String valorParametro) {
        String[] params = { valorParametro };

        try {
            Open();

            db.delete(ViagemModel.TABELA, colunaParametro + " = ?", params);
        }
        finally {
            Close();
        }
    }

    private ContentValues getContentValues(ViagemModel viagemModel) {
        ContentValues values = new ContentValues();

        values.put(ViagemModel.COLUNA_ID_USUARIO, viagemModel.getIdUsuario());
        values.put(ViagemModel.COLUNA_TITULO, viagemModel.getTitulo());
        values.put(ViagemModel.COLUNA_TOTAL_VIAJANTES, viagemModel.getTotalViajantes());
        values.put(ViagemModel.COLUNA_DURACAO, viagemModel.getDuracao());
        values.put(ViagemModel.COLUNA_DATA_CRIACAO, viagemModel.getDataCriacao());
        values.put(ViagemModel.COLUNA_TOTAL, viagemModel.getTotal());

        return values;
    }

    private ViagemModel CursorParaViagem(Cursor cursor) {
        ViagemModel v = new ViagemModel();

        v.setId(cursor.getInt(0));
        v.setIdUsuario(cursor.getInt(1));
        v.setTitulo(cursor.getString(2));
        v.setTotalViajantes(cursor.getInt(3));
        v.setDuracao(cursor.getInt(4));
        v.setDataCriacao(cursor.getString(5));
        v.setTotal(cursor.getDouble(6));

        return v;
    }
}
