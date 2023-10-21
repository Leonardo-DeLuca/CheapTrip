package database.model;

public class RefeicoesModel {
    public static final String TABELA = "viagem_refeicoes";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_VIAGEM = "id_viagem",
            COLUNA_CUSTO_ESTIMADO_REFEICAO = "custo_estimado_refeicao",
            COLUNA_REFEICOES_DIA = "refeicoes_dia",
            COLUNA_TOTAL = "total",
            COLUNA_ADICIONOU_NA_VIAGEM = "adicionou_viagem";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_ID_VIAGEM + " integer not null, "
            + COLUNA_CUSTO_ESTIMADO_REFEICAO + " real, "
            + COLUNA_REFEICOES_DIA + " integer, "
            + COLUNA_ADICIONOU_NA_VIAGEM + " integer not null, "
            + COLUNA_TOTAL + " real not null, "
            + "foreign key (" + COLUNA_ID_VIAGEM + ") references viagem (_id))";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_ID_VIAGEM, COLUNA_CUSTO_ESTIMADO_REFEICAO, COLUNA_REFEICOES_DIA, COLUNA_TOTAL, COLUNA_ADICIONOU_NA_VIAGEM };
    }

    private int id;
    private int idViagem;
    private double custoEstimadoRefeicao;
    private int refeicoesDia;
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

    public double getCustoEstimadoRefeicao() {
        return custoEstimadoRefeicao;
    }

    public void setCustoEstimadoRefeicao(double custoEstimadoRefeicao) {
        this.custoEstimadoRefeicao = custoEstimadoRefeicao;
    }

    public int getRefeicoesDia() {
        return refeicoesDia;
    }

    public void setRefeicoesDia(int refeicoesDia) {
        this.refeicoesDia = refeicoesDia;
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
