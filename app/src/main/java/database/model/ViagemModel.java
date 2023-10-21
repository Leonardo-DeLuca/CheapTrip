package database.model;

public class ViagemModel {
    public static final String TABELA = "viagem";

    public static final String
        COLUNA_ID = "_id",
        COLUNA_ID_USUARIO = "id_usuario",
        COLUNA_TITULO = "titulo",
        COLUNA_DATA_CRIACAO = "data_criacao";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_ID_USUARIO + " integer not null, "
            + COLUNA_TITULO + " text not null, "
            + COLUNA_DATA_CRIACAO + " integer not null"
            + ")";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_ID_USUARIO, COLUNA_TITULO, COLUNA_DATA_CRIACAO };
    }

    private int id;
    private int idUsuario;
    private String titulo;
    private int dataCriacao;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(int dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
