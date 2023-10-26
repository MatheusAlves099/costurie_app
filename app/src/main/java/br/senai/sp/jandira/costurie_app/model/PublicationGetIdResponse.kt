package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class PublicationGetIdResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("titulo") var titulo: String = "",
    @SerializedName("descricao") var descricao: String = "",
    @SerializedName("data_publicacao") var data_publicacao: String = "",
    @SerializedName("hora") var hora: String = "",
    @SerializedName("usuario") var usuario: UsersTagResponse,
    @SerializedName("anexos") var anexos: List<AnexoGetResponse> = mutableListOf(),
    @SerializedName("tags") var tags: MutableList<TagGetPublicationResponse> = mutableListOf()
)
