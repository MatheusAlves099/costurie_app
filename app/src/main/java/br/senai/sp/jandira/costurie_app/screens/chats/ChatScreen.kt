package br.senai.sp.jandira.costurie_app.screens.chats

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.components.MessageBar
import br.senai.sp.jandira.costurie_app.components.ReceivedMessagePicture
import br.senai.sp.jandira.costurie_app.components.ReceivedMesssage
import br.senai.sp.jandira.costurie_app.components.SendMessagePicture
import br.senai.sp.jandira.costurie_app.components.SendMesssage
import br.senai.sp.jandira.costurie_app.service.chat.ChatClient
import br.senai.sp.jandira.costurie_app.service.chat.MensagensResponse
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ChatViewModel
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Principal1
import br.senai.sp.jandira.costurie_app.ui.theme.Principal2
import coil.compose.AsyncImage
import com.google.gson.Gson
import io.socket.client.Socket
import org.json.JSONObject

@SuppressLint("SuspiciousIndentation")
@Composable
fun ChatScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    client: ChatClient,
    socket: Socket,
    idUsuario: Int,
    chatViewModel: ChatViewModel
) {

    val idChat = chatViewModel.idChat
    val idUser2 = chatViewModel.idUser2
    var foto = chatViewModel.foto
    var nome = chatViewModel.nome

    Log.d("Luizão", "idChat: $idChat, idUser: $idUser2")

    var message by remember {
        mutableStateOf("")
    }

    var isLongPressActive by remember { mutableStateOf(false) }

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Principal2
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                var listaMensagens by remember {
                    mutableStateOf(
                        MensagensResponse(
                            status = 0,
                            message = "",
                            id_chat = "",
                            usuarios = listOf(),
                            data_criacao = "",
                            hora_criacao = "",
                            mensagens = mutableStateListOf()
                        )
                    )
                }

                 //Ouça o evento do socket
                socket.emit("listMessages", idChat)
                LaunchedEffect(listaMensagens) {
                    // Ouça o evento do socket
                    socket.on("receive_message") { args ->
                        args.let { d ->
                            if (d.isNotEmpty()) {
                                val data = d[0]
                                if (data.toString().isNotEmpty()) {
                                    val mensagens =
                                        Gson().fromJson(
                                            data.toString(),
                                            MensagensResponse::class.java
                                        )

                                    listaMensagens = mensagens
                                    if (mensagens.id_chat == idChat) {
                                        Log.w("XAMUEEL EU TE AMO", "IDCHAT = ${mensagens.id_chat}", )
                                       listaMensagens = mensagens
                                   }
                                    Log.w("XAMUEEL NÃO", "IDCHAT = ${mensagens.id_chat}", )
                                } else {
                                    listaMensagens = MensagensResponse(
                                        status = 0,
                                        message = "",
                                        id_chat = "",
                                        usuarios = listOf(),
                                        data_criacao = "",
                                        hora_criacao = "",
                                        mensagens = mutableStateListOf()
                                    )
                                }
                            }
                        }
                    }
                }


//                socket.emit("listMessages", idChat)
//                LaunchedEffect(listaMensagens) {
//                    // Ouça o evento do socket
//                    socket.on("receive_message") { args ->
//                        args.let { d ->
//                            if (d.isNotEmpty()) {
//                                val data = d[0]
//                                if (data.toString().isNotEmpty()) {
//                                    val mensagens =
//                                        Gson().fromJson(data.toString(), MensagensResponse::class.java)
//
//                                    if (mensagens.id_chat == idChat) {
//                                        listaMensagens = mensagens
//                                    }
//                                    Log.e("TesteIndo", "${listaMensagens.mensagens.reversed()}")
//                                }
//                            }
//                        }
//                    }
//                }


                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.retangulo_topo),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        alignment = Alignment.TopEnd
                    )
                    Row(
                        modifier = Modifier
                            .width(370.dp)
                            .padding(top = 15.dp, start = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    navController.navigate("home")
                                }
                        )

                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(Color(255, 255, 255, 255))
                        ) {
                            AsyncImage(
                                model = foto,
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 5.dp, end = 2.dp)
                                    .clip(shape = RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column {
                            Text(
                                text = nome,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .width(250.dp)
                                    .height(20.dp),
                                fontSize = 15.sp,
                                color = Principal1
                            )

                            Text(
                                text = "Online",
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .width(250.dp),
                                fontSize = 12.sp,
                                color = Color(252, 246, 255, 179)
                            )
                        }
                    }
                }
                Column {
                    Log.i("listaMensagens", "ChatScreen: ${listaMensagens.mensagens}")
                    if (listaMensagens.mensagens != null) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(12.dp)
                                .pointerInput(Unit) {

                                    detectTransformGestures { _, pan, _, _ ->
                                        if (pan != Offset(0f, 0f)) {
                                            isLongPressActive = false
                                        }
                                    }
                                },
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            reverseLayout = true
                        ) {
                            items(listaMensagens.mensagens.asReversed()) {

                                if (it.message == "" && it.image != ""){
                                    if (it.messageTo == idUsuario) {
                                        SendMessagePicture(
                                            mensagem = "",
                                            time = it.hora_criacao!!.substring(0,5),
                                            foto = it.image,
                                            onDelete = {
                                                socket.on("deleteMessage") { args ->
                                                    args.let { d ->
                                                        if (d.isNotEmpty()) {
                                                            val idMessageDeleted = d[0] as String
                                                            listaMensagens = listaMensagens.copy(
                                                                mensagens = listaMensagens.mensagens
                                                                    .filterNot { it.message == idMessageDeleted }
                                                                    .toMutableList()
                                                            )
                                                            Log.e("IDMANO", "${idMessageDeleted}")
                                                        }
                                                    }
                                                }
                                                client.deleteMessage(it._id.toString())
                                            }
                                        )
                                    } else {
                                        ReceivedMessagePicture(
                                            mensagem = "",
                                            time = it.hora_criacao!!.substring(0,5),
                                            foto = it.image
                                        )

                                    }
                                }else{
                                    if (it.messageTo == idUsuario) {
                                        ReceivedMesssage(
                                            message = it.message,
                                            time = it.hora_criacao!!.substring(0, 5)
                                        )
                                    } else {
                                        SendMesssage(
                                            message = it.message,
                                            time = it.hora_criacao!!.substring(0, 5),
                                            onDelete = {
                                                socket.on("deleteMessage") { args ->
                                                    args.let { d ->
                                                        if (d.isNotEmpty()) {
                                                            val idMessageDeleted = d[0] as String
                                                            listaMensagens = listaMensagens.copy(
                                                                mensagens = listaMensagens.mensagens
                                                                    .filterNot { it.message == idMessageDeleted }
                                                                    .toMutableList()
                                                            )
                                                            Log.e("IDMANO", "${idMessageDeleted}")
                                                        }
                                                    }
                                                }
                                                client.deleteMessage(it._id.toString())
                                            },
                                            isLongPressActive = isLongPressActive
                                        )
                                    }
                                }

                            }
                        }
                    }

                    MessageBar(
                        value = message,
                        onValueChange = {
                            message = it
                            val json = JSONObject().apply {
                                put("messageBy", idUsuario)
                                put("messageTo", idUser2)
                                put("message", message)
                                put("image", "")
                                put("chatId", idChat)
                            }

                            Log.e("JSON", "$json")
                            // val jsonString = Json.encodeToString(json)

                            client.sendMessage(json)
                        },
                        chatViewModel = chatViewModel,
                        navController = navController,
                        client = client,
                        idUsuario = 0
                    )
                }
            }
        }
    }
}