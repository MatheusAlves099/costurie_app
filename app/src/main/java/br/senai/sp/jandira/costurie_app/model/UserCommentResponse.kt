package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class UserCommentResponse(
    @SerializedName("nome_de_usuario") var nome_de_usuario: String = "",
    @SerializedName("foto") var foto: String = "",
)
