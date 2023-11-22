package br.senai.sp.jandira.costurie_app.service.chat

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class ChatClient() {
    private val socket: Socket = IO.socket("http://10.107.144.27:3001")
    //private val socket: Socket = IO.socket("http://192.168.3.7:3001")

    fun connect(idUsuario: Int) {
        socket.connect()

        socket.on(Socket.EVENT_CONNECT) {
            println("Connected to server")
            socket.emit("listContacts", idUsuario)
        }

        socket.on("message") { args ->
            val message = args[0] as String
            println("Received message: $message")
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            println("Disconnected from server")
        }
    }

    fun sendMessage(message: JSONObject) {
        socket.emit("message", message)
    }

    @Synchronized
    fun getSocket(): Socket {
        return socket
    }

    @Synchronized
    fun disconnect() {
        socket.disconnect()
    }
}