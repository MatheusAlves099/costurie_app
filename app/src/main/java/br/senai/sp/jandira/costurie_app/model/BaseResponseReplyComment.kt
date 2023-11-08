package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class BaseResponseReplyComment(
    @SerializedName("resposta") var resposta: List<ReplyCommentResponse>,
    @SerializedName("comentario") var comentario: List<CommentResponse>,
    @SerializedName("usuario") var usuario: UserCommentResponse
)
