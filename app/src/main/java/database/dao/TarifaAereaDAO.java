package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import database.DBHelper;
import database.model.GasolinaModel;
import database.model.RefeicoesModel;
import database.model.TarifaAereaModel;

public class TarifaAereaDAO extends Base {

    public TarifaAereaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(TarifaAereaModel tarifaAereaModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            db.insert(TarifaAereaModel.TABELA, null, getContentValues(tarifaAereaModel));
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public TarifaAereaModel selectBy(String colunaParametro, String valorParametro) {
        TarifaAereaModel tarifaAereaRetornada = null;
        String[] params = { valorParametro };

        try {
            Open();

            Cursor cursor = db.query(TarifaAereaModel.TABELA, TarifaAereaModel.getColunas(), colunaParametro + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                tarifaAereaRetornada = CursorParaTarifaAerea(cursor);
                cursor.close();
            }
        }
        finally {
            Close();
        }

        return tarifaAereaRetornada;
    }

    public void update(TarifaAereaModel tarifaAereaModel) {
        String[] params = { String.valueOf(tarifaAereaModel.getIdViagem()) };

        try {
            Open();

            db.update(TarifaAereaModel.TABELA, getContentValues(tarifaAereaModel), TarifaAereaModel.COLUNA_ID_VIAGEM + " = ?", params);
        }
        finally {
            Close();
        }
    }

    private ContentValues getContentValues(TarifaAereaModel tarifaAereaModel) {
        ContentValues values = new ContentValues();

        values.put(TarifaAereaModel.COLUNA_ID_VIAGEM, tarifaAereaModel.getIdViagem());
        values.put(TarifaAereaModel.COLUNA_CUSTO_ESTIMADO_PESSOA, tarifaAereaModel.getCustoEstimadoPessoa());
        values.put(TarifaAereaModel.COLUNA_ALUGUEL_VEICULO, tarifaAereaModel.getAluguelVeiculo());
        values.put(TarifaAereaModel.COLUNA_TOTAL, tarifaAereaModel.getTotal());
        values.put(TarifaAereaModel.COLUNA_ADICIONOU_NA_VIAGEM, tarifaAereaModel.getAdicionouViagem());

        return values;
    }

    private TarifaAereaModel CursorParaTarifaAerea(Cursor cursor) {
        TarifaAereaModel t = new TarifaAereaModel();

        t.setId(cursor.getInt(0));
        t.setIdViagem(cursor.getInt(1));
        t.setCustoEstimadoPessoa(cursor.getDouble(2));
        t.setAluguelVeiculo(cursor.getDouble(3));
        t.setTotal(cursor.getInt(4));
        t.setAdicionouViagem(cursor.getInt(5));

        return t;
    }
}
