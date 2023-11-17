package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class O_ANDRE_PRECISA_PATRONIZAR_O_ID_USUARIO(
    @SerializedName("id") val id: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("foto") val foto: String,
    @SerializedName("nome_de_usuario") val nome_de_usuario: String,
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String,
    @SerializedName("id_localizacao") val id_localizacao: Any,
)
