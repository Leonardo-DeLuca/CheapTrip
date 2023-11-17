package api.model;

import java.io.Serializable;
import java.util.Date;

public class ViagemCustoAereo implements Serializable {
    private double custoPessoa;
    private double custoAluguelVeiculo;
    public double getCustoPessoa() {
        return custoPessoa;
    }

    public void setCustoPessoa(double custoPessoa) {
        this.custoPessoa = custoPessoa;
    }

    public double getCustoAluguelVeiculo() {
        return custoAluguelVeiculo;
    }

    public void setCustoAluguelVeiculo(double custoAluguelVeiculo) {
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }

}
