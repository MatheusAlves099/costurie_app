package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class BaseResponseIdPublication(
    @SerializedName("publicacao") var publicacao: PublicationGetIdResponse
)
