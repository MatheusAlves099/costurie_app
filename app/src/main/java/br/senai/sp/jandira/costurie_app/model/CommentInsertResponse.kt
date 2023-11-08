package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class CommentInsertResponse(
    @SerializedName("id_publicacao") var id_publicacao: Int = 0,
    @SerializedName("id_usuario") var id_usuario: Int = 0,
    @SerializedName("mensagem") var mensagem: String = "",
)
