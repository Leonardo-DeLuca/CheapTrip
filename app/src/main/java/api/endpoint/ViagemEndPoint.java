package api.endpoint;

import api.model.DTOEnviar;
import api.model.Resposta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ViagemEndPoint {
    @POST("api/cadastro/viagem")
    Call<Resposta> postViagem(@Body DTOEnviar dtoEnviar);

    @GET("api/listar/viagem/{viagemID}")
    Call<Resposta> getViagem(@Query("viagemId") int viagemId);

    @GET("api/listar/viagem")
    Call<Resposta> getViagemPath(@Path("viagemId") int viagemId);

}
