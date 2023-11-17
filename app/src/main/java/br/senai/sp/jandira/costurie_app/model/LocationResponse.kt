package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("id_localizacao") val id_localizacao: Int,
    @SerializedName("cidade") val cidade: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("bairro") val bairro: String
)
