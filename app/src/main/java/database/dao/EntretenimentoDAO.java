package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import database.DBHelper;
import database.model.EntretenimentoModel;
import database.model.HospedagemModel;

public class EntretenimentoDAO extends Base {

    public EntretenimentoDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(EntretenimentoModel entretenimentoModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            db.insert(EntretenimentoModel.TABELA, null, getContentValues(entretenimentoModel));
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public EntretenimentoModel selectBy(String colunaParametro, String valorParametro) {
        EntretenimentoModel entretenimentoRetornado = null;
        String[] params = { valorParametro };

        try {
            Open();

            Cursor cursor = db.query(EntretenimentoModel.TABELA, EntretenimentoModel.getColunas(), colunaParametro + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                entretenimentoRetornado = CursorParaEntretenimento(cursor);
                cursor.close();
            }
        }
        finally {
            Close();
        }

        return entretenimentoRetornado;
    }

    public void update(EntretenimentoModel entretenimentoModel) {
        String[] params = { String.valueOf(entretenimentoModel.getIdViagem()) };

        try {
            Open();

            db.update(EntretenimentoModel.TABELA, getContentValues(entretenimentoModel), EntretenimentoModel.COLUNA_ID_VIAGEM + " = ?", params);
        }
        finally {
            Close();
        }
    }

    private ContentValues getContentValues(EntretenimentoModel entretenimentoModel) {
        ContentValues values = new ContentValues();

        values.put(EntretenimentoModel.COLUNA_ID_VIAGEM, entretenimentoModel.getIdViagem());
        values.put(EntretenimentoModel.COLUNA_ENTRETENIMENTO1, entretenimentoModel.getEntretenimento1());
        values.put(EntretenimentoModel.COLUNA_ENTRETENIMENTO2, entretenimentoModel.getEntretenimento2());
        values.put(EntretenimentoModel.COLUNA_ENTRETENIMENTO3, entretenimentoModel.getEntretenimento3());
        values.put(EntretenimentoModel.COLUNA_VALOR_ENTRETENIMENTO1, entretenimentoModel.getValorEntretenimento1());
        values.put(EntretenimentoModel.COLUNA_VALOR_ENTRETENIMENTO2, entretenimentoModel.getValorEntretenimento2());
        values.put(EntretenimentoModel.COLUNA_VALOR_ENTRETENIMENTO3, entretenimentoModel.getValorEntretenimento3());
        values.put(EntretenimentoModel.COLUNA_TOTAL, entretenimentoModel.getTotal());
        values.put(EntretenimentoModel.COLUNA_ADICIONOU_NA_VIAGEM, entretenimentoModel.getAdicionouViagem());

        return values;
    }

    private EntretenimentoModel CursorParaEntretenimento(Cursor cursor) {
        EntretenimentoModel e = new EntretenimentoModel();

        e.setId(cursor.getInt(0));
        e.setIdViagem(cursor.getInt(1));
        e.setEntretenimento1(cursor.getString(2));
        e.setEntretenimento2(cursor.getString(3));
        e.setEntretenimento3(cursor.getString(4));
        e.setValorEntretenimento1(cursor.getDouble(5));
        e.setValorEntretenimento2(cursor.getDouble(6));
        e.setValorEntretenimento3(cursor.getDouble(7));
        e.setTotal(cursor.getInt(8));
        e.setAdicionouViagem(cursor.getInt(9));

        return e;
    }
}
