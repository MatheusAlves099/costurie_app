package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class AnexoGetResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("anexo") var anexo: String = "",
    @SerializedName("id_publicacao") var id_publicacao: Int = 0,
)
