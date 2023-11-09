package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class ReplyCommentGetResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("data_resposta") var data_resposta: String = "",
    @SerializedName("hora_resposta") var hora_resposta: String = "",
    @SerializedName("mensagem") var mensagem: String = "",
    @SerializedName("id_comentario") var id_comentario: Int = 0,
    @SerializedName("id_usuario") var id_usuario: Int = 0,
    @SerializedName("usuario") var usuario: UserCommentResponse
)
