package br.senai.sp.jandira.costurie_app.model

import com.google.gson.annotations.SerializedName

data class BaseResponseReplyCommentGet(
    @SerializedName("message") var message: String = "",
    @SerializedName("status") var status: String = "",
    @SerializedName("respostas") var resposta: List<ReplyCommentGetResponse>
)
