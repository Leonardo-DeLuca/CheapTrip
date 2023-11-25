package api.model;

import java.io.Serializable;

public class Resposta implements Serializable {

    private boolean sucesso;
    private String mensagem;
    private int dados;

    public Resposta() {

    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        mensagem = mensagem;
    }

    public int getDados() {
        return dados;
    }

    public void setDados(int dados) {
        dados = dados;
    }
}
