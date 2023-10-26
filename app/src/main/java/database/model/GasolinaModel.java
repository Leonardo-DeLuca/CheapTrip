package database.model;

public class GasolinaModel {
    public static final String TABELA = "viagem_gasolina";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_VIAGEM = "id_viagem",
            COLUNA_TOTAL_ESTIMADO_KMS = "total_estimado_kms",
            COLUNA_MEDIA_KMS_LITRO = "media_kms_litro",
            COLUNA_CUSTO_MEDIO_LITRO = "custo_medio_litro",
            COLUNA_TOTAL_VEICULOS = "total_veiculos",
            COLUNA_ADICIONOU_NA_VIAGEM = "adicionou_viagem",
            COLUNA_TOTAL = "total";

    public static final String CREATE_TABLE = "create table " + TABELA
            + "("
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_ID_VIAGEM + " integer not null, "
            + COLUNA_TOTAL_ESTIMADO_KMS + " real, "
            + COLUNA_MEDIA_KMS_LITRO + " real, "
            + COLUNA_CUSTO_MEDIO_LITRO + " real, "
            + COLUNA_ADICIONOU_NA_VIAGEM + " integer not null, "
            + COLUNA_TOTAL + " real not null, "
            + COLUNA_TOTAL_VEICULOS + " integer, "
            + "foreign key (" + COLUNA_ID_VIAGEM + ") references viagem (_id) ON DELETE CASCADE)";

    public static final String DROP_TABLE = "drop table if exists " + TABELA + ";";

    public static final String[] getColunas() {
        return new String[] { COLUNA_ID, COLUNA_ID_VIAGEM, COLUNA_TOTAL_ESTIMADO_KMS, COLUNA_MEDIA_KMS_LITRO, COLUNA_CUSTO_MEDIO_LITRO, COLUNA_TOTAL_VEICULOS, COLUNA_TOTAL, COLUNA_ADICIONOU_NA_VIAGEM };
    }

    private int id;
    private int idViagem;
    private double totalEstimadoKms;
    private double mediaKmsLitro;
    private double custoMedioLitro;
    private int totalVeiculos;
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

    public double getTotalEstimadoKms() {
        return totalEstimadoKms;
    }

    public void setTotalEstimadoKms(double totalEstimadoKms) {
        this.totalEstimadoKms = totalEstimadoKms;
    }

    public double getMediaKmsLitro() {
        return mediaKmsLitro;
    }

    public void setMediaKmsLitro(double mediaKmsLitro) {
        this.mediaKmsLitro = mediaKmsLitro;
    }

    public double getCustoMedioLitro() {
        return custoMedioLitro;
    }

    public void setCustoMedioLitro(double custoMedioLitro) {
        this.custoMedioLitro = custoMedioLitro;
    }

    public int getTotalVeiculos() {
        return totalVeiculos;
    }

    public void setTotalVeiculos(int totalVeiculos) {
        this.totalVeiculos = totalVeiculos;
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
