package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import database.DBHelper;
import database.model.EntretenimentoModel;
import database.model.GasolinaModel;

public class GasolinaDAO extends Base {

    public GasolinaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(GasolinaModel gasolinaModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(GasolinaModel.COLUNA_ID_VIAGEM, gasolinaModel.getIdViagem());
            values.put(GasolinaModel.COLUNA_TOTAL_ESTIMADO_KMS, gasolinaModel.getTotalEstimadoKms());
            values.put(GasolinaModel.COLUNA_CUSTO_MEDIO_LITRO, gasolinaModel.getCustoMedioLitro());
            values.put(GasolinaModel.COLUNA_TOTAL_VEICULOS, gasolinaModel.getTotalVeiculos());
            values.put(GasolinaModel.COLUNA_TOTAL, gasolinaModel.getTotal());
            values.put(GasolinaModel.COLUNA_ADICIONOU_NA_VIAGEM, gasolinaModel.getAdicionouViagem());

            db.insert(GasolinaModel.TABELA, null, values);
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

    private GasolinaModel CursorParaGasolina(Cursor cursor) {
        GasolinaModel g = new GasolinaModel();

        g.setId(cursor.getInt(0));
        g.setIdViagem(cursor.getInt(1));
        g.setTotalEstimadoKms(cursor.getDouble(2));
        g.setCustoMedioLitro(cursor.getDouble(3));
        g.setTotalVeiculos(cursor.getInt(4));
        g.setTotal(cursor.getInt(5));
        g.setAdicionouViagem(cursor.getInt(6));

        return g;
    }
}
