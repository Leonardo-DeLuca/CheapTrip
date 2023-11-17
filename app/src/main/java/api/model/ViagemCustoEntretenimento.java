package api.model;

import java.io.Serializable;
import java.util.Date;

public class ViagemCustoEntretenimento implements Serializable {

    private String entretenimento;
    private int valor;

    public String getEntretenimento() {
        return entretenimento;
    }

    public void setEntretenimento(String entretenimento) {
        this.entretenimento = entretenimento;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

}
