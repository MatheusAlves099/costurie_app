package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class TagGetPublicationResponse(
    @SerializedName("id_tag") val id: Int = 0,
    @SerializedName("nome") val nome_tag: String = "",
    @SerializedName("imagem") val imagem: String = "",
    @SerializedName("id_categoria") val id_categoria: Int = 0,
    @SerializedName("nome_categoria") val nome_categoria: String = "",
)
