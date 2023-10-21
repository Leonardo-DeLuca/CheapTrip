package database.model;

public class HospedagemModel {
    public static final String TABELA = "viagem_hospedagem";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_VIAGEM = "id_viagem",
            COLUNA_CUSTO_MEDIO_NOITE = "custo_medio_noite",
            COLUNA_TOTAL_NOITES = "total_noites",
            COLUNA_TOTAL_QUARTOS = "total_quartos",
            COLUNA_TOTAL = "total",
            COLUNA_ADICIONOU_NA_VIAGEM = "adicionou_viagem";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_ID_VIAGEM + " integer not null, "
            + COLUNA_CUSTO_MEDIO_NOITE + " real, "
            + COLUNA_TOTAL_NOITES + " integer, "
            + COLUNA_TOTAL_QUARTOS + " integer, "
            + COLUNA_ADICIONOU_NA_VIAGEM + " integer not null, "
            + COLUNA_TOTAL + " real not null, "
            + "foreign key (" + COLUNA_ID_VIAGEM + ") references viagem (_id))";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_ID_VIAGEM, COLUNA_CUSTO_MEDIO_NOITE, COLUNA_TOTAL_NOITES, COLUNA_TOTAL_QUARTOS, COLUNA_TOTAL, COLUNA_ADICIONOU_NA_VIAGEM };
    }

    private int id;
    private int idViagem;
    private double custoMedioNoite;
    private int totalNoites;
    private int totalQuartos;
    private double total;
    private int adicionouViagem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public double getCustoMedioNoite() {
        return custoMedioNoite;
    }

    public void setCustoMedioNoite(double custoMedioNoite) {
        this.custoMedioNoite = custoMedioNoite;
    }

    public int getTotalNoites() {
        return totalNoites;
    }

    public void setTotalNoites(int totalNoites) {
        this.totalNoites = totalNoites;
    }

    public int getTotalQuartos() {
        return totalQuartos;
    }

    public void setTotalQuartos(int totalQuartos) {
        this.totalQuartos = totalQuartos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getAdicionouViagem() {
        return adicionouViagem;
    }

    public void setAdicionouViagem(int adicionouViagem) {
        this.adicionouViagem = adicionouViagem;
    }
}
