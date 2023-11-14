package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("data_comentario") var data_comentario: String = "",
    @SerializedName("hora_comentario") var hora_comentario: String = "",
    @SerializedName("mensagem") var mensagem: String = "",
    @SerializedName("id_publicacao") var id_publicacao: Int = 0,
    @SerializedName("id_usuario") var id_usuario: Int = 0,
    @SerializedName("respostas") var respostas: Any = false,
    @SerializedName("usuario") var usuario: UserCommentResponse,
)
