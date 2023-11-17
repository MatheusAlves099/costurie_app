package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class UserGetIDResponse (
    @SerializedName("usuario") val usuario: UserGetResponse,
)