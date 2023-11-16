package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class LocationGetResponse(
    @SerializedName("id_localizacao") var id_localizacao: Int = 0,
    @SerializedName("cidade") var cidade: String = "",
    @SerializedName("estado") var estado: String = "",
    @SerializedName("bairro") var bairro: String = "",
)
