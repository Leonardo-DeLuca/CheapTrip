package api;

import java.util.ArrayList;
import java.util.List;

import api.model.ViagemCustoAereo;
import api.model.ViagemCustoEntretenimento;
import api.model.ViagemCustoGasolina;
import api.model.ViagemCustoHospedagem;
import api.model.ViagemCustoRefeicao;
import database.model.EntretenimentoModel;
import database.model.GasolinaModel;
import database.model.HospedagemModel;
import database.model.RefeicoesModel;
import database.model.TarifaAereaModel;

// Classe para converter nossos models para os models esperados pela API. Mais simples do que mudar nossas
// regras de negócio e nomenclaturas.
public class Conversor {
    public static ViagemCustoAereo converterTarifaAerea(TarifaAereaModel tarifaAereaModel) {
        return new ViagemCustoAereo(tarifaAereaModel.getCustoEstimadoPessoa(), tarifaAereaModel.getAluguelVeiculo());
    }

    public static List<ViagemCustoEntretenimento> converterEntretenimentos(EntretenimentoModel entretenimentoModel) {
        List<ViagemCustoEntretenimento> listaViagemCustoEntretenimento = new ArrayList<>();

        if (entretenimentoModel.getEntretenimento1() != null && !"".equals(entretenimentoModel.getEntretenimento1())) {
            listaViagemCustoEntretenimento.add(new ViagemCustoEntretenimento(entretenimentoModel.getEntretenimento1(), entretenimentoModel.getValorEntretenimento1()));
        }

        if (entretenimentoModel.getEntretenimento2() != null && !"".equals(entretenimentoModel.getEntretenimento2())) {
            listaViagemCustoEntretenimento.add(new ViagemCustoEntretenimento(entretenimentoModel.getEntretenimento2(), entretenimentoModel.getValorEntretenimento2()));
        }

        if (entretenimentoModel.getEntretenimento3() != null && !"".equals(entretenimentoModel.getEntretenimento3())) {
            listaViagemCustoEntretenimento.add(new ViagemCustoEntretenimento(entretenimentoModel.getEntretenimento3(), entretenimentoModel.getValorEntretenimento3()));
        }

        return listaViagemCustoEntretenimento;
    }

    public static ViagemCustoGasolina converterGasolina(GasolinaModel gasolinaModel) {
        return new ViagemCustoGasolina(gasolinaModel.getTotalEstimadoKms(), gasolinaModel.getMediaKmsLitro(), gasolinaModel.getCustoMedioLitro(), gasolinaModel.getTotalVeiculos());
    }

    public static ViagemCustoHospedagem converterHospedagem(HospedagemModel hospedagemModel) {
        return new ViagemCustoHospedagem(hospedagemModel.getCustoMedioNoite(), hospedagemModel.getTotalNoites(), hospedagemModel.getTotalQuartos());
    }

    public static ViagemCustoRefeicao converterRefeicao(RefeicoesModel refeicoesModel) {
        return new ViagemCustoRefeicao(refeicoesModel.getCustoEstimadoRefeicao(), refeicoesModel.getRefeicoesDia());
    }
}
