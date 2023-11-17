package api;

import api.endpoint.ViagemEndPoint;
import api.model.DTOEnviar;
import api.model.Resposta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String URL_ROOT = "http://api.genialsaude.com.br/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void getViagem(int viagemId, final Callback<Resposta> callback) {
        ViagemEndPoint endpoint = retrofit.create(ViagemEndPoint.class);
        Call<Resposta> call = endpoint.getViagemPath(viagemId);
        call.enqueue(callback);
    }

    public static void postViagem(DTOEnviar dtoEnviar, final Callback<Resposta> callback) {
        ViagemEndPoint endpoint = retrofit.create(ViagemEndPoint.class);
        Call<Resposta> call = endpoint.postViagem(dtoEnviar);
        call.enqueue(callback);
    }
}
