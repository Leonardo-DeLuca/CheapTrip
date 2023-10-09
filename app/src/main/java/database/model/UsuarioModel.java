package database.model;

public class UsuarioModel {
    public static final String TABELA = "usuario";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_NOME_COMPLETO = "nome_completo",
            COLUNA_USUARIO = "usuario",
            COLUNA_EMAIL = "email",
            COLUNA_SENHA = "senha";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_NOME_COMPLETO + " text not null, "
            + COLUNA_USUARIO + " text not null, "
            + COLUNA_EMAIL + " text not null, "
            + COLUNA_SENHA + " text not null"
            + ")";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_NOME_COMPLETO, COLUNA_USUARIO, COLUNA_EMAIL, COLUNA_SENHA };
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private String nomeCompleto;
    private String usuario;
    private String email;
    private String senha;
}
