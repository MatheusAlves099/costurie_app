package br.senai.sp.jandira.costurie_app.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    //IP DO MURYLLO, NÃO TIRA SÓ COMENTAAA
    //private const val BASE_URL = "http://10.107.144.5:3003"

    //private const val BASE_URL = "http://192.168.3.7:3000"

    private const val BASE_URL = "http://10.107.144.27:3000"



    object HttpClientProvider {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Tempo limite de conexão
            .readTimeout(60, TimeUnit.SECONDS)    // Tempo limite de leitura
            .build()
    }
    fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(HttpClientProvider.client)
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