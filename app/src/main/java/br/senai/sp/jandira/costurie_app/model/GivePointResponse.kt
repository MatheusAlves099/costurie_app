package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class GivePointResponse(
    @SerializedName("status") var status: Int = 0,
    @SerializedName("message") var message: String = "",
)
