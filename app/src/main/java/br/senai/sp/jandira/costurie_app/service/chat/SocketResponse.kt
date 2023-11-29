package br.senai.sp.jandira.costurie_app.service.chat

data class SocketResponse(
    var users: List<ChatRoom>,
    var id_user: Int
)
