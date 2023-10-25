package br.senai.sp.jandira.costurie_app.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitFactory {

    //IP DO MURYLLO, NÃO TIRA SÓ COMENTAAA
    //private const val BASE_URL = "http://192.168.3.7:3000"

    private const val BASE_URL = "http://10.107.144.21:3000"

    //private const val BASE_URL = "http://192.168.1.2:3000"

    fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private const val URL_IBGE = "https://servicodados.ibge.gov.br/api/v1/localidades/"

    fun getInstance2(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL_IBGE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}