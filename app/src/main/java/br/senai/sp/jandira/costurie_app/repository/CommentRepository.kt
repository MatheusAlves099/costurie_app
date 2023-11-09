package br.senai.sp.jandira.costurie_app.repository

import br.senai.sp.jandira.costurie_app.model.BaseCommentResponse
import br.senai.sp.jandira.costurie_app.model.BaseResponseComment
import br.senai.sp.jandira.costurie_app.model.BaseResponseIdPublication
import br.senai.sp.jandira.costurie_app.model.BaseResponseReplyComment
import br.senai.sp.jandira.costurie_app.model.BaseResponseReplyCommentGet
import br.senai.sp.jandira.costurie_app.model.GivePointResponse
import br.senai.sp.jandira.costurie_app.service.CommentService
import br.senai.sp.jandira.costurie_app.service.RetrofitFactory
import br.senai.sp.jandira.costurie_app.service.UserService
import com.google.gson.JsonObject
import retrofit2.Response

class CommentRepository {
    private val apiService = RetrofitFactory.getInstance().create(CommentService::class.java)

    suspend fun getCommentByPublication(token: String, id: Int): Response<BaseCommentResponse> {

        return apiService.getCommentByPublication(id, token)
    }

    suspend fun postComment(
        token: String,
        id_publicacao: Int,
        id_usuario: Int,
        mensagem: String
    ): Response<BaseResponseComment> {
        val requestBody = JsonObject().apply {
            addProperty("id_publicacao", id_publicacao)
            addProperty("id_usuario", id_usuario)
            addProperty("mensagem", mensagem)

        }

        return apiService.createComment(requestBody, token)
    }

    suspend fun deleteComment(token: String, id: Int): Response<JsonObject> {
        return apiService.deleteComment(token, id)
    }

    suspend fun postReplyComment(
        token: String,
        id_usuario: Int,
        id_comentario: Int,
        mensagem: String
    ): Response<BaseResponseReplyComment> {
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id_usuario)
            addProperty("id_comentario", id_comentario)
            addProperty("mensagem", mensagem)
        }

        return apiService.createReplyComment(requestBody, token)
    }

    suspend fun getReplyComment(token: String, id: Int): Response<BaseResponseReplyCommentGet> {

        return apiService.getReplyComment(id, token)
    }
}