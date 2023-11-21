package br.senai.sp.jandira.costurie_app.service.chat

data class Mensagem(
    var _id: String?,
    var messageBy: Int,
    var messageTo: Int,
    var message: String,
    var image: String,
    var data_criacao: String?,
    var hora_criacao: String?,
    var chatId: String,
    var __v: Int?
)
