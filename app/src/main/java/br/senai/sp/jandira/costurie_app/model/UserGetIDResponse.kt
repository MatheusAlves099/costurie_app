package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class UserGetIDResponse (
    @SerializedName("usuario") val usuario: UserGetResponse,
    @SerializedName("localizacao") var localizacao: Any,
    @SerializedName("publicacoes") var publicacoes: List<PublicationGetResponse>,
)