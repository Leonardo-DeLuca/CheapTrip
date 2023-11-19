package api.model;

import java.io.Serializable;
import java.util.Date;

public class ViagemCustoGasolina implements Serializable {

    private double totalEstimadoKM;
    private double mediaKMLitro;
    private double custoMedioLitro;
    private double custoPorPessoa;

    public ViagemCustoGasolina(double totalEstimadoKM, double mediaKMLitro, double custoMedioLitro, double custoPorPessoa) {
        this.totalEstimadoKM = totalEstimadoKM;
        this.mediaKMLitro = mediaKMLitro;
        this.custoMedioLitro = custoMedioLitro;
        this.custoPorPessoa = custoPorPessoa;
    }

    public double getTotalEstimadoKM() {
        return totalEstimadoKM;
    }

    public void setTotalEstimadoKM(double totalEstimadoKM) {
        this.totalEstimadoKM = totalEstimadoKM;
    }

    public double getMediaKMLitro() {
        return mediaKMLitro;
    }

    public void setMediaKMLitro(double mediaKMLitro) {
        this.mediaKMLitro = mediaKMLitro;
    }

    public double getCustoMedioLitro() {
        return custoMedioLitro;
    }

    public void setCustoMedioLitro(double custoMedioLitro) {
        this.custoMedioLitro = custoMedioLitro;
    }

    public double getCustoPorPessoa() {
        return custoPorPessoa;
    }

    public void setCustoPorPessoa(double custoPorPessoa) {
        this.custoPorPessoa = custoPorPessoa;
    }
}
