package br.senai.sp.jandira.costurie_app.screens.chats

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.ModalDeleteMessage
import br.senai.sp.jandira.costurie_app.service.chat.ChatClient
import br.senai.sp.jandira.costurie_app.service.chat.SocketResponse
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ChatViewModel
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Principal2
import coil.compose.AsyncImage
import com.google.gson.Gson
import io.socket.client.Socket

@Composable
fun ChatListScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    localStorage: Storage,
    client: ChatClient,
    socket: Socket,
    idUsuario: Int,
    chatViewModel: ChatViewModel
) {

    var listaContatos by remember {
        mutableStateOf(
            SocketResponse(
                users = listOf()
            )
        )
    }
    Log.d("LISTA1", "ChatListScreen: $listaContatos")
    Log.d("ID", "ChatListScreen: $idUsuario")
    // Ouça o evento do socket
    socket.on("receive_contacts") { args ->
        args.let { d ->
            if (d.isNotEmpty()) {
                val data = d[0]
                if (data.toString().isNotEmpty()) {
                    val chat = Gson().fromJson(data.toString(), SocketResponse::class.java)

                    listaContatos = chat
                }
            }
        }
    }

    Log.d("LISTA2", "ChatListScreen: $listaContatos")
    var context = LocalContext.current

    var pesquisaState by remember {
        mutableStateOf("")
    }

    var isLongPressActive by remember { mutableStateOf(false) }

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLongPressActive)
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
                                .width(390.dp)
                                .height(80.dp)
                                .padding(start = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
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

                            ModalDeleteMessage(
                                lifecycleScope,
                                localStorage,
                                navController
                            )
                        }
                    } else {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, top = 10.dp),
                        color = Color.Black,
                        text = stringResource(id = R.string.conversas_titulo),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    CustomOutlinedTextField2(
                        value = pesquisaState,
                        onValueChange = {
                            pesquisaState = it
                            (pesquisaState)
                        },
                        label = stringResource(id = R.string.conversas_label),
                        borderColor = Color.Transparent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp)
                            .padding(horizontal = 32.dp)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp)),
                        searchIcon = true
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(listaContatos.users) {

                            Log.d("LISTA3", "ChatListScreen: $listaContatos")
                            Log.d("LISTA4", "ChatListScreen: ${listaContatos.users}")
                            var contato = it.users.filter { user -> user.id != idUsuario }

                            Log.e("oii", "aquiiii: ${contato[0].id}")

                            Card(
                                modifier = Modifier
                                    .size(380.dp, 85.dp)
                                    .padding(start = 32.dp, top = 4.dp, bottom = 4.dp)
                                    .pointerInput(Unit) {
                                        detectTransformGestures { _, pan, _, _ ->
                                            if (pan != Offset(0f, 0f)) {
                                                isLongPressActive = true
                                            }
                                        }
                                    }
                                    .clickable {

                                        Log.e("Luizão - ChatScreen", "$contato", )
                                        Log.e("Luizão - ChatScreen", "id: ${it.id_chat}", )


                                        navController.navigate("chat")

                                        chatViewModel.idChat = it.id_chat
                                        chatViewModel.idUser2 = contato[0].id
                                        chatViewModel.foto = contato[0].foto
                                        chatViewModel.nome = contato[0].nome
                                        socket.emit("listMessages", it.id_chat)
                                        Log.e("luiz", "ChatScreen: ${contato[0].id}")
                                    },
                                backgroundColor = (if (isLongPressActive) Principal2 else Color.White),
                                shape = RoundedCornerShape(15.dp),
                                elevation = AppBarDefaults.TopAppBarElevation,

                                ) {
                                Row(
                                    modifier = Modifier
                                        .width(300.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(shape = RoundedCornerShape(10.dp))
                                            .background(Color(168, 155, 255, 102))
                                    ) {
                                        AsyncImage(
                                            model = contato[0].foto,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(bottom = 5.dp, end = 2.dp)
                                                .clip(shape = RoundedCornerShape(10.dp)),
                                            contentScale = ContentScale.Crop
                                        )

                                        if (isLongPressActive) Image(
                                            painterResource(id = R.drawable.check_icon),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .offset(x = 40.dp, y = 40.dp)
                                                .clip(shape = RoundedCornerShape(10.dp)),
                                            contentScale = ContentScale.Crop
                                        ) else {

                                        }
                                    }

                                    Column(

                                    ) {
                                        Text(
                                            text = contato[0].nome,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .width(230.dp)
                                                .height(22.dp),
                                            fontSize = 15.sp,
                                            color = Contraste
                                        )

                                        Text(
                                            text = "Boa noite!",
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .width(230.dp)
                                                .height(22.dp),
                                            fontSize = 15.sp,
                                            color = Contraste2
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .height(80.dp)
                                            .padding(top = 24.dp),
                                        Arrangement.SpaceBetween,
                                        Alignment.CenterHorizontally
                                    ) {
                                        Canvas(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .pointerInput(Unit) {
                                                    detectTransformGestures { _, pan, _, _ ->
                                                        if (pan != Offset(0f, 0f)) {
                                                            isLongPressActive = true
                                                        }
                                                    }
                                                },
                                            onDraw = {
                                                drawCircle(
                                                    color = if (isLongPressActive) Color.Transparent else Destaque1
                                                )
                                            }
                                        )

                                        Text(
                                            text = it.hora_criacao,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .width(30.dp)
                                                .height(20.dp),
                                            fontSize = 10.sp,
                                            color = Contraste2
                                        )
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }
    }
}

