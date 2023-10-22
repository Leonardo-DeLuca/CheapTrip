package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import database.DBHelper;
import database.model.HospedagemModel;
import database.model.RefeicoesModel;

public class HospedagemDAO extends Base {
    public HospedagemDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(HospedagemModel hospedagemModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(HospedagemModel.COLUNA_ID_VIAGEM, hospedagemModel.getIdViagem());
            values.put(HospedagemModel.COLUNA_CUSTO_MEDIO_NOITE, hospedagemModel.getCustoMedioNoite());
            values.put(HospedagemModel.COLUNA_TOTAL_NOITES, hospedagemModel.getTotalNoites());
            values.put(HospedagemModel.COLUNA_TOTAL_QUARTOS, hospedagemModel.getTotalQuartos());
            values.put(HospedagemModel.COLUNA_TOTAL, hospedagemModel.getTotal());
            values.put(HospedagemModel.COLUNA_ADICIONOU_NA_VIAGEM, hospedagemModel.getAdicionouViagem());
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public HospedagemModel selectBy(String colunaParametro, String valorParametro) {
        HospedagemModel hospedagemRetornada = null;
        String[] params = { valorParametro };

        try {
            Open();

            Cursor cursor = db.query(HospedagemModel.TABELA, HospedagemModel.getColunas(), colunaParametro + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                hospedagemRetornada = CursorParaHospedagem(cursor);
                cursor.close();
            }
        }
        finally {
            Close();
        }

        return hospedagemRetornada;
    }

    private HospedagemModel CursorParaHospedagem(Cursor cursor) {
        HospedagemModel h = new HospedagemModel();

        h.setId(cursor.getInt(0));
        h.setIdViagem(cursor.getInt(1));
        h.setCustoMedioNoite(cursor.getDouble(2));
        h.setTotalNoites(cursor.getInt(3));
        h.setTotalQuartos(cursor.getInt(4));
        h.setTotal(cursor.getInt(5));
        h.setAdicionouViagem(cursor.getInt(6));

        return h;
    }
}
