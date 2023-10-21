package database.model;

public class TarifaAereaModel {
    public static final String TABELA = "viagem_tarifa_aerea";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_VIAGEM = "id_viagem",
            COLUNA_CUSTO_ESTIMADO_PESSOA = "custo_estimado_pessoa",
            COLUNA_ALUGUEL_VEICULO = "aluguel_veiculo",
            COLUNA_ADICIONOU_NA_VIAGEM = "adicionou_viagem",
            COLUNA_TOTAL = "total";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_ID_VIAGEM + " integer not null, "
            + COLUNA_CUSTO_ESTIMADO_PESSOA + " real, "
            + COLUNA_ALUGUEL_VEICULO + " real, "
            + COLUNA_TOTAL + " real not null, "
            + COLUNA_ADICIONOU_NA_VIAGEM + " integer not null, "
            + "foreign key (" + COLUNA_ID_VIAGEM + ") references viagem (_id))";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_ID_VIAGEM, COLUNA_CUSTO_ESTIMADO_PESSOA, COLUNA_ALUGUEL_VEICULO, COLUNA_ADICIONOU_NA_VIAGEM };
    }

    private int id;
    private int idViagem;
    private double custoEstimadoPessoa;
    private double aluguelVeiculo;
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

    public double getCustoEstimadoPessoa() {
        return custoEstimadoPessoa;
    }

    public void setCustoEstimadoPessoa(double custoEstimadoPessoa) {
        this.custoEstimadoPessoa = custoEstimadoPessoa;
    }

    public double getAluguelVeiculo() {
        return aluguelVeiculo;
    }

    public void setAluguelVeiculo(double aluguelVeiculo) {
        this.aluguelVeiculo = aluguelVeiculo;
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
