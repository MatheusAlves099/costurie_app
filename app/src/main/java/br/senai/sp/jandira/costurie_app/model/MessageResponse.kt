package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("messageBy") var message_by: Int = 0,
    @SerializedName("messageTo") var message_to: Int = 0,
    @SerializedName("message") var message: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("data_criacao") var data_criacao: String = "",
    @SerializedName("hora_criacao") var hora_criacao: String = "",
    @SerializedName("chatId") var chat_id: String = "",
)
