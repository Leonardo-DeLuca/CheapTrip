package database.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViagemModel {
    public static final String TABELA = "viagem";

    public static final String
        COLUNA_ID = "_id",
        COLUNA_ID_USUARIO = "id_usuario",
        COLUNA_TITULO = "titulo",
        COLUNA_TOTAL_VIAJANTES = "total_viajantes",
        COLUNA_DURACAO = "duracao",
        COLUNA_DATA_CRIACAO = "data_criacao",
        COLUNA_TOTAL = "total";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_ID_USUARIO + " integer not null, "
            + COLUNA_TITULO + " text not null, "
            + COLUNA_TOTAL_VIAJANTES + " integer not null, "
            + COLUNA_DURACAO + " integer not null, "
            + COLUNA_DATA_CRIACAO + " integer not null, "
            + COLUNA_TOTAL + " real not null"
            + ")";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_ID_USUARIO, COLUNA_TITULO, COLUNA_TOTAL_VIAJANTES, COLUNA_DURACAO, COLUNA_DATA_CRIACAO, COLUNA_TOTAL };
    }

    private int id;
    private int idUsuario;
    private String titulo;
    private int totalViajantes;
    private int duracao;
    private long dataCriacao;
    private double total;


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

    public int getTotalViajantes() {
        return totalViajantes;
    }

    public void setTotalViajantes(int totalViajantes) {
        this.totalViajantes = totalViajantes;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public long getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(long dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDataCriacaoString() {
        Locale locale = new Locale("pt", "BR");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", locale);
        Date dataCriacao = new Date(getDataCriacao());

        return dateFormat.format(dataCriacao);
    }
}
