package com.example.fran.mapagoogle.service;

import com.example.fran.mapagoogle.entidade.Oficina;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {


    @FormUrlEncoded
    @POST("convert")
    Call<RespostaServidor> converterUnidade(Oficina usuario);
}
