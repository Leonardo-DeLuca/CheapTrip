package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import database.DBHelper;
import database.model.EntretenimentoModel;
import database.model.RefeicoesModel;

public class RefeicoesDAO extends Base {
    public RefeicoesDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(RefeicoesModel refeicoesModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(RefeicoesModel.COLUNA_ID_VIAGEM, refeicoesModel.getIdViagem());
            values.put(RefeicoesModel.COLUNA_CUSTO_ESTIMADO_REFEICAO, refeicoesModel.getCustoEstimadoRefeicao());
            values.put(RefeicoesModel.COLUNA_REFEICOES_DIA, refeicoesModel.getRefeicoesDia());
            values.put(RefeicoesModel.COLUNA_TOTAL, refeicoesModel.getTotal());
            values.put(RefeicoesModel.COLUNA_ADICIONOU_NA_VIAGEM, refeicoesModel.getAdicionouViagem());

            db.insert(RefeicoesModel.TABELA, null, values);
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public RefeicoesModel selectBy(String colunaParametro, String valorParametro) {
        RefeicoesModel refeicoesRetornada = null;
        String[] params = { valorParametro };

        try {
            Open();

            Cursor cursor = db.query(RefeicoesModel.TABELA, RefeicoesModel.getColunas(), colunaParametro + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                refeicoesRetornada = CursorParaRefeicoes(cursor);
                cursor.close();
            }
        }
        finally {
            Close();
        }

        return refeicoesRetornada;
    }

    private RefeicoesModel CursorParaRefeicoes(Cursor cursor) {
        RefeicoesModel r = new RefeicoesModel();

        r.setId(cursor.getInt(0));
        r.setIdViagem(cursor.getInt(1));
        r.setCustoEstimadoRefeicao(cursor.getDouble(2));
        r.setRefeicoesDia(cursor.getInt(3));
        r.setTotal(cursor.getInt(4));
        r.setAdicionouViagem(cursor.getInt(5));

        return r;
    }
}
