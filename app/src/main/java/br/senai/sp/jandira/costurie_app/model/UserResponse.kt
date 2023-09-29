package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("nome") var nome: String = "",
    @SerializedName("descricao") var descricao: String = "",
    @SerializedName("foto") var foto: String = "",
    @SerializedName("nome_de_usuario") var nome_de_usuario: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("senha") var senha: String = "",
//    @SerializedName("cidade") var cidade: String = "",
//    @SerializedName("bairro") var bairro: String = "",
//    @SerializedName("estado") var estado: String = "",
//    @SerializedName("id_localizacao") var id_localizacao: Int? = 0,
//    @SerializedName("tags") var tag: List<TagsResponse>
)
