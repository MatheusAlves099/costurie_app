package br.senai.sp.jandira.costurie_app.service

import br.senai.sp.jandira.costurie_app.model.BaseResponse
import br.senai.sp.jandira.costurie_app.model.CityResponse
import br.senai.sp.jandira.costurie_app.model.NeighborhoodResponse
import br.senai.sp.jandira.costurie_app.model.StateResponse
import br.senai.sp.jandira.costurie_app.model.UserGetIDResponse
import br.senai.sp.jandira.costurie_app.model.UserGetResponse
import br.senai.sp.jandira.costurie_app.model.UserJsonResponse
import br.senai.sp.jandira.costurie_app.model.UserResponse
import br.senai.sp.jandira.costurie_app.model.UserTagsResponse
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    //https://super-hare-shoulder-pads.cyclic.cloud/

    @Headers("Content-Type: application/json")
    @POST("/usuario/cadastro")
    suspend fun createUser(@Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("/usuario/login")
    suspend fun postUser(@Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("/usuario/esqueceu_a_senha")
    suspend fun requestPasswordReset(@Body requestBody: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("/usuario/validar_token")
    suspend fun tokenValidation(@Body requestBody: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @PUT("/usuario/atualizar_senha")
    suspend fun updateUserPassword(@Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("estados")
    suspend fun getStates(): Response<List<StateResponse>>

    @Headers("Content-Type: application/json")
    @GET("estado/{UF}/cidades")
    suspend fun getCitys(@Path("UF") UF: String): Response<List<CityResponse>>

    @Headers("Content-Type: application/json")
    @GET("cidade/{ID}/bairros/")
    suspend fun getNeighborhood(@Path("ID") ID: Int): Response<List<NeighborhoodResponse>>

    @Headers("Content-Type: application/json")
    @POST("/usuario/inserir_localizacao")
    suspend fun postLocation(@Body requestBody: JsonObject): Response<JsonObject>

    @GET("/usuario/meu_perfil/{id}")
    suspend fun getUser(
        @Path("id") id: Int,
        @Header("x-access-token") token: String
    ): Response<UserGetIDResponse>

    @Headers("Content-Type: application/json")
    @PUT("/usuario/editar_perfil")
    suspend fun updateUser(
        @Body body: JsonObject,
        @Header("x-access-token") token: String
    ): Response<UserResponse>

    @Headers("Content-Type: application/json")
    @PUT("/usuario/personalizar_perfil")
    suspend fun updateUserNamePicDesc(
        @Body body: JsonObject
    ): Response<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("/usuario/inserir_localizacao")
    suspend fun updateLocation(
        @Body body: JsonObject,
        @Header("x-access-token") token: String
    ): Response<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("/tag/inserir_tags")
    suspend fun updateUserTags(
        @Body body: JsonObject,
        @Header("x-access-token") token: String
    ): Response<UserTagsResponse>
}