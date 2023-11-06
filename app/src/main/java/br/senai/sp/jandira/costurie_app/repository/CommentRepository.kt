package br.senai.sp.jandira.costurie_app.repository

import br.senai.sp.jandira.costurie_app.model.BaseCommentResponse
import br.senai.sp.jandira.costurie_app.model.BaseResponseIdPublication
import br.senai.sp.jandira.costurie_app.service.CommentService
import br.senai.sp.jandira.costurie_app.service.RetrofitFactory
import br.senai.sp.jandira.costurie_app.service.UserService
import retrofit2.Response

class CommentRepository {
    private val apiService = RetrofitFactory.getInstance().create(CommentService::class.java)

    suspend fun getCommentByPublication(token: String, id: Int): Response<BaseCommentResponse> {

        return apiService.getCommentByPublication(id, token)
    }
}