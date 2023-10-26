package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class BaseResponsePublication(
    @SerializedName("publicacoes") var publicacoes: List<PublicationGetResponse>,

)
