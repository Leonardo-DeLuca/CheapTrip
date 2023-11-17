package api.model;

import java.io.Serializable;
import java.util.Date;

public class ViagemCustoGasolina implements Serializable {

    private int totalEstimadoKM;
    private double mediaKMLitro;
    private double custoMedioLitro;
    private int custoPorPessoa;

    public int getTotalEstimadoKM() {
        return totalEstimadoKM;
    }

    public void setTotalEstimadoKM(int totalEstimadoKM) {
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

    public int getCustoPorPessoa() {
        return custoPorPessoa;
    }

    public void setCustoPorPessoa(int custoPorPessoa) {
        this.custoPorPessoa = custoPorPessoa;
    }
}
