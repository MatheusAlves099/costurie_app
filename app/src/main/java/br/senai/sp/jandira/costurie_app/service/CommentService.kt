package br.senai.sp.jandira.costurie_app.service

import br.senai.sp.jandira.costurie_app.model.BaseCommentResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CommentService {

    @GET("/comentario/select_by_id_publicacao/{id}")
    suspend fun getCommentByPublication(
        @Path("id") id: Int,
        @Header("x-access-token") token: String
    ): Response<BaseCommentResponse>

}