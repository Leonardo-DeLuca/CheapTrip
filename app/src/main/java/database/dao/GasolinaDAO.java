package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import database.DBHelper;
import database.model.GasolinaModel;
import database.model.ViagemModel;

public class GasolinaDAO extends Base {

    public GasolinaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(GasolinaModel gasolinaModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            db.insert(GasolinaModel.TABELA, null, getContentValues(gasolinaModel));
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public GasolinaModel selectBy(String colunaParametro, String valorParametro) {
        GasolinaModel gasolinaRetornada = null;
        String[] params = { valorParametro };

        try {
            Open();

            Cursor cursor = db.query(GasolinaModel.TABELA, GasolinaModel.getColunas(), colunaParametro + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                gasolinaRetornada = CursorParaGasolina(cursor);
                cursor.close();
            }
        }
        finally {
            Close();
        }

        return gasolinaRetornada;
    }

    public void update(GasolinaModel gasolinaModel) {
        String[] params = { String.valueOf(gasolinaModel.getIdViagem()) };

        try {
            Open();

            db.update(GasolinaModel.TABELA, getContentValues(gasolinaModel), GasolinaModel.COLUNA_ID_VIAGEM + " = ?", params);
        }
        finally {
            Close();
        }
    }

    private ContentValues getContentValues(GasolinaModel gasolinaModel) {
        ContentValues values = new ContentValues();

        values.put(GasolinaModel.COLUNA_ID_VIAGEM, gasolinaModel.getIdViagem());
        values.put(GasolinaModel.COLUNA_TOTAL_ESTIMADO_KMS, gasolinaModel.getTotalEstimadoKms());
        values.put(GasolinaModel.COLUNA_MEDIA_KMS_LITRO, gasolinaModel.getMediaKmsLitro());
        values.put(GasolinaModel.COLUNA_CUSTO_MEDIO_LITRO, gasolinaModel.getCustoMedioLitro());
        values.put(GasolinaModel.COLUNA_TOTAL_VEICULOS, gasolinaModel.getTotalVeiculos());
        values.put(GasolinaModel.COLUNA_TOTAL, gasolinaModel.getTotal());
        values.put(GasolinaModel.COLUNA_ADICIONOU_NA_VIAGEM, gasolinaModel.getAdicionouViagem());

        return values;
    }

    private GasolinaModel CursorParaGasolina(Cursor cursor) {
        GasolinaModel g = new GasolinaModel();

        g.setId(cursor.getInt(0));
        g.setIdViagem(cursor.getInt(1));
        g.setTotalEstimadoKms(cursor.getDouble(2));
        g.setMediaKmsLitro(cursor.getDouble(3));
        g.setCustoMedioLitro(cursor.getDouble(4));
        g.setTotalVeiculos(cursor.getInt(5));
        g.setTotal(cursor.getInt(6));
        g.setAdicionouViagem(cursor.getInt(7));

        return g;
    }
}
