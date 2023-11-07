package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class BaseResponseGivePoint(
    @SerializedName("id_usuario") var id_usuario: Int = 0,
    @SerializedName("id_publicacao") var id_publicacao: Int = 0,
)
