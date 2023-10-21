package database.model;

public class EntretenimentoModel {
    public static final String TABELA = "viagem_entretenimento";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_VIAGEM = "id_viagem",
            COLUNA_ENTRETENIMENTO1 = "entretenimento1",
            COLUNA_ENTRETENIMENTO2 = "entretenimento2",
            COLUNA_ENTRETENIMENTO3 = "entretenimento3",
            COLUNA_VALOR_ENTRETENIMENTO1 = "valor_entretenimento1",
            COLUNA_VALOR_ENTRETENIMENTO2 = "valor_entretenimento2",
            COLUNA_VALOR_ENTRETENIMENTO3 = "valor_entretenimento3",
            COLUNA_ADICIONOU_NA_VIAGEM = "adicionou_viagem",
            COLUNA_TOTAL = "total";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_ID_VIAGEM + " integer not null, "
            + COLUNA_ENTRETENIMENTO1 + " text, "
            + COLUNA_ENTRETENIMENTO2 + " text, "
            + COLUNA_ENTRETENIMENTO3 + " text, "
            + COLUNA_VALOR_ENTRETENIMENTO1 + " real, "
            + COLUNA_VALOR_ENTRETENIMENTO2 + " real, "
            + COLUNA_VALOR_ENTRETENIMENTO3 + " real, "
            + COLUNA_TOTAL + " real not null, "
            + COLUNA_ADICIONOU_NA_VIAGEM + " integer not null, "
            + "foreign key (" + COLUNA_ID_VIAGEM + ") references viagem (_id))";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_ID_VIAGEM, COLUNA_ENTRETENIMENTO1, COLUNA_ENTRETENIMENTO2, COLUNA_ENTRETENIMENTO3,
                COLUNA_VALOR_ENTRETENIMENTO1, COLUNA_VALOR_ENTRETENIMENTO2, COLUNA_VALOR_ENTRETENIMENTO3, COLUNA_TOTAL, COLUNA_ADICIONOU_NA_VIAGEM  };
    }

    private int id;
    private int idViagem;
    private String entretenimento1;
    private String entretenimento2;
    private String entretenimento3;
    private double valorEntretenimento1;
    private double valorEntretenimento2;
    private double valorEntretenimento3;
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

    public String getEntretenimento1() {
        return entretenimento1;
    }

    public void setEntretenimento1(String entretenimento1) {
        this.entretenimento1 = entretenimento1;
    }

    public String getEntretenimento2() {
        return entretenimento2;
    }

    public void setEntretenimento2(String entretenimento2) {
        this.entretenimento2 = entretenimento2;
    }

    public String getEntretenimento3() {
        return entretenimento3;
    }

    public void setEntretenimento3(String entretenimento3) {
        this.entretenimento3 = entretenimento3;
    }

    public double getValorEntretenimento1() {
        return valorEntretenimento1;
    }

    public void setValorEntretenimento1(double valorEntretenimento1) {
        this.valorEntretenimento1 = valorEntretenimento1;
    }

    public double getValorEntretenimento2() {
        return valorEntretenimento2;
    }

    public void setValorEntretenimento2(double valorEntretenimento2) {
        this.valorEntretenimento2 = valorEntretenimento2;
    }

    public double getValorEntretenimento3() {
        return valorEntretenimento3;
    }

    public void setValorEntretenimento3(double valorEntretenimento3) {
        this.valorEntretenimento3 = valorEntretenimento3;
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
