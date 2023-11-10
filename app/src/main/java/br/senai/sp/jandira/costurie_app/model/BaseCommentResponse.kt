package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class BaseCommentResponse(
    @SerializedName("comentarios") var comentarios: List<CommentResponse>,
)
