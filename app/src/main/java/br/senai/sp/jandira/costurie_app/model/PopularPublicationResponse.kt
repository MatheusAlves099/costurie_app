package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class PopularPublicationResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("titulo") var titulo: String = "",
    @SerializedName("descricao") var descricao: String = "",
    @SerializedName("data_publicacao") var data_publicacao: String = "",
    @SerializedName("hora") var hora: String = "",
    @SerializedName("id_usuario") var id_usuario: Int = 0,
    @SerializedName("anexos") var anexos: List<AnexoGetResponse> = mutableListOf(),

)
