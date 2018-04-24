package com.example.fran.mapagoogle.RestClient;

import com.example.fran.mapagoogle.entidade.Oficina;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by handerson on 23/04/18.
 */

public interface RetrofitService {
    //headers é caso de api autenticada
    //@Headers("X-Mashape-Key: AuuyclCPjcmshv2iOPq190OpzLrMp1FJWwejsnJrdfwOUr4h44")

    @FormUrlEncoded
    @POST("oficinas")
    Call<Oficina> cadastarOficinas(@Field("rua") String rua,
                                   @Field("numero") int numero,
                                   @Field("bairro") String bairro,
                                   @Field("cep") String cep,
                                   @Field("nome") String nome,
                                   @Field("cnpj") String cnpj,
                                   @Field("telefone") String telefone,
                                   @Field("email") String email,
                                   @Field("senha") String senha);
    @GET("oficina")
    Call<List<Oficina>> getOficinas();

}
