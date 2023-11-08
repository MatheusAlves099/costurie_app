package br.senai.sp.jandira.costurie_app.service

import br.senai.sp.jandira.costurie_app.model.BaseCommentResponse
import br.senai.sp.jandira.costurie_app.model.BaseResponseComment
import br.senai.sp.jandira.costurie_app.model.BaseResponseReplyComment
import br.senai.sp.jandira.costurie_app.model.PublicationResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {

    @GET("/comentario/select_by_id_publicacao/{id}")
    suspend fun getCommentByPublication(
        @Path("id") id: Int,
        @Header("x-access-token") token: String
    ): Response<BaseCommentResponse>

    @Headers("Content-Type: application/json")
    @POST("/comentario/inserir")
    suspend fun createComment(
        @Body body: JsonObject,
        @Header("x-access-token") token: String
    ): Response<BaseResponseComment>

    @Headers("Content-Type: application/json")
    @DELETE("/comentario/{id}")
    suspend fun deleteComment(
        @Header("x-access-token") token: String,
        @Path("id") id: Int
    ): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("/respostas_comentario/inserir")
    suspend fun createReplyComment(
        @Body body: JsonObject,
        @Header("x-access-token") token: String
    ): Response<BaseResponseReplyComment>

}