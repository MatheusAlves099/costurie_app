package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class BaseResponsePopularPublication(
    @SerializedName("publicacao") var publicacao: List<PopularPublicationResponse>
)
