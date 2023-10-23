package database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import database.DBHelper;
import database.model.UsuarioModel;

public class UsuarioDAO extends Base {

    public UsuarioDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(UsuarioModel usuarioModel) {
        long linhasAfetadas = 0;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(UsuarioModel.COLUNA_NOME_COMPLETO, usuarioModel.getNomeCompleto());
            values.put(UsuarioModel.COLUNA_USUARIO, usuarioModel.getUsuario());
            values.put(UsuarioModel.COLUNA_EMAIL, usuarioModel.getEmail());
            values.put(UsuarioModel.COLUNA_SENHA, usuarioModel.getSenha());

            linhasAfetadas = db.insert(UsuarioModel.TABELA, null, values);
        }
        finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long editarSenha(UsuarioModel usuarioModel) {
        long linhasAfetadas = 0;
        String[] params = {String.valueOf(usuarioModel.getId())};

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(UsuarioModel.COLUNA_SENHA, usuarioModel.getSenha());

            linhasAfetadas = db.update(UsuarioModel.TABELA, values, UsuarioModel.COLUNA_ID + " = ?", params);
        }
        finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long editarImagem(UsuarioModel usuarioModel) {
        long linhasAfetadas = 0;
        String[] params = {String.valueOf(usuarioModel.getId())};

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(UsuarioModel.COLUNA_IMAGEM, usuarioModel.getImagem());

            linhasAfetadas = db.update(UsuarioModel.TABELA, values, UsuarioModel.COLUNA_ID + " = ?", params);
        }
        finally {
            Close();
        }

        return linhasAfetadas;
    }

    public UsuarioModel selectBy(String colunaParametro, String valorParametro) {
        UsuarioModel usuarioRetornado = null;
        String[] params = { valorParametro };

        try {
            Open();

            Cursor cursor = db.query(UsuarioModel.TABELA, UsuarioModel.getColunas(), colunaParametro + " = ?", params, null, null, null);

            if (cursor.moveToFirst()) {
                usuarioRetornado = CursorParaUsuario(cursor);
                cursor.close();
            }
        }
        finally {
            Close();
        }

        return usuarioRetornado;
    }

    private UsuarioModel CursorParaUsuario(Cursor cursor) {
        UsuarioModel u = new UsuarioModel();

        u.setId(cursor.getInt(0));
        u.setNomeCompleto(cursor.getString(1));
        u.setUsuario(cursor.getString(2));
        u.setEmail(cursor.getString(3));
        u.setSenha(cursor.getString(4));
        u.setImagem(cursor.getBlob(5));

        return u;
    }
}
