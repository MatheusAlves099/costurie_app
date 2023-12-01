package br.senai.sp.jandira.costurie_app.screens.expandedPublication

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.ButtonGivePoint
import br.senai.sp.jandira.costurie_app.components.ButtonSettings
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.GoogleButton
import br.senai.sp.jandira.costurie_app.components.GradientButton
import br.senai.sp.jandira.costurie_app.components.GradientButtonSmall
import br.senai.sp.jandira.costurie_app.components.GradientButtonTag
import br.senai.sp.jandira.costurie_app.components.GradientButtonTags
import br.senai.sp.jandira.costurie_app.components.ModalEditDeletePublication
import br.senai.sp.jandira.costurie_app.components.ModalFilter
import br.senai.sp.jandira.costurie_app.components.ModalTags2
import br.senai.sp.jandira.costurie_app.components.ModalTagsPublication
import br.senai.sp.jandira.costurie_app.components.ProgressBar
import br.senai.sp.jandira.costurie_app.function.deleteUserSQLite
import br.senai.sp.jandira.costurie_app.model.BaseResponseIdPublication
import br.senai.sp.jandira.costurie_app.model.PublicationGetResponse
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.model.UsersTagResponse
import br.senai.sp.jandira.costurie_app.repository.PublicationRepository
import br.senai.sp.jandira.costurie_app.repository.UserRepository
import br.senai.sp.jandira.costurie_app.screens.expandedComment.ExpandedCommentScreen
import br.senai.sp.jandira.costurie_app.service.chat.ChatClient
import br.senai.sp.jandira.costurie_app.service.chat.MensagensResponse
import br.senai.sp.jandira.costurie_app.service.chat.UserChat
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ChatViewModel
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ViewModelID
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.viewModel.TagPublicationViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserTagViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandedPublicationScreen(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavController,
    viewModel: TagPublicationViewModel,
    localStorage: Storage,
    viewModelId: ViewModelID,
    socket: Socket,
    client: ChatClient,
    chatViewModel: ChatViewModel
) {
    var context = LocalContext.current

    val dadaUser = UserRepositorySqlite(context).findUsers()


    var listUsuario by remember {
        mutableStateOf(
            listOf<UserChat>()
        )
    }

    Log.e("estamos aquiiiii", "${listUsuario}")

    var newChat by remember {
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

    var commentState by remember {
        mutableStateOf("")
    }

    var isLoading = false

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val scope = rememberCoroutineScope()



    var id = localStorage.lerValor(context, "id_publicacao")

    val publicationState = remember { mutableStateOf<BaseResponseIdPublication?>(null) }

    val isOpen = remember { mutableStateOf(false) }

    // Chame essa função para abrir a modal
    fun openModal() {
        isOpen.value = true
    }

    var fotoDoCara by remember {
        mutableStateOf("")
    }

    var btnColor by remember {
        mutableStateOf(listOf<Color>(Color.Transparent, Color.Transparent))
    }

    var textColor by remember {
        mutableStateOf(listOf<Color>(Destaque1, Destaque2))
    }

    Log.i("color", "ExpandedPublicationScreen: ${btnColor}")
    Log.i("color", "ExpandedPublicationScreen: ${textColor}")

    suspend fun getUser() {
        val userRepository = UserRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = userRepository.getUser(user.id.toInt(), user.token)

        if (response.isSuccessful) {
            Log.e(MainActivity::class.java.simpleName, "Requisição bem sucedida, Publicação")
            Log.e("usuario", "usuario: ${response.body()} ")

            fotoDoCara = response.body()?.usuario?.foto!!
            Log.e("foto1", "foto: $fotoDoCara", )

        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("FOTO", "FOTO: $errorBody")
        }
    }

    suspend fun getPublicationById() {
        val publicationRepository = PublicationRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = publicationRepository.getPublicationById(user.token, id!!.toInt())

        if (response.isSuccessful) {
            Log.e(MainActivity::class.java.simpleName, "Requisição bem sucedida, Publicação")
            Log.e("publicação", "publicação: ${response.body()} ")

            publicationState.value = response.body()
            viewModel.tags = response.body()?.publicacao?.tags

            viewModelId.id_perfil = response.body()?.publicacao?.usuario?.id!!.toLong()
            viewModelId.nome_perfil = response.body()?.publicacao?.usuario?.nome_de_usuario!!
            viewModelId.foto_perfil = response.body()?.publicacao?.usuario?.foto!!

        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("EDICAO DE PERFIL", "updateUser: $errorBody")
        }
    }

    val publicationRepository = PublicationRepository()
    val array = UserRepositorySqlite(context).findUsers()
    val user = array[0]

    suspend fun postGivePoint() {
        val response = publicationRepository.givePoint(user.token, user.id.toInt(), id!!.toInt())
        Log.d("postGivePoint", "Resposta: $response")
        if (response.isSuccessful) {
            Log.d("point", "postGivePoint: $response")
            val publications = response.body()
            Toast.makeText(
                context,
                publications!!.message,
                Toast.LENGTH_SHORT
            ).show()
            btnColor = listOf(Destaque1, Destaque2)
            textColor = listOf(Color.White, Color.White)
            Log.i("ponto", "dei o ponto: ${publications}")
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("CURTIR UMA PUBLICAÇÃO", "Erro: $errorBody")
            Toast.makeText(
                context,
                "Erro ao dar ponto em na publicação: $errorBody",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    suspend fun postRemovePoint() {
        val response = publicationRepository.removePoint(user.token, user.id.toInt(), id!!.toInt())
        Log.d("postRemovePoint", "Resposta: $response")
        if (response.isSuccessful) {
            Log.d("point", "postRemovePoint: $response")
            val publications = response.body()
            Toast.makeText(
                context,
                publications!!.message,
                Toast.LENGTH_SHORT
            ).show()
            btnColor = listOf(Color.Transparent, Color.Transparent)
            textColor = listOf(Destaque1, Destaque2)
            Log.i("ponto", "tirei o ponto: ${publications}")
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("CURTIR UMA PUBLICAÇÃO", "Erro: $errorBody")
            Toast.makeText(
                context,
                "Erro ao dar ponto em na publicação: $errorBody",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    suspend fun getPoint() {
        val response = publicationRepository.getPoint(user.token, user.id.toInt(), id!!.toInt())

        if (response.isSuccessful) {
            val body = response.body()
            if (body!!.curtida) {
                btnColor = listOf(Destaque1, Destaque2)
                textColor = listOf(Color.White, Color.White)
                isClicked = true
            } else {
                btnColor = listOf(Color.Transparent, Color.Transparent)
                textColor = listOf(Destaque1, Destaque2)
                isClicked = false
            }
        }
    }

    LaunchedEffect(key1 = true) {

        getPublicationById()
        getUser()
        getPoint()

        Log.e("PUBLICAÇÃO", "A tal da publication explodida: ${getPublicationById()}")
        Log.e("foto2", "foto: $fotoDoCara", )
    }





    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetShape = RoundedCornerShape(20.dp),
                sheetElevation = 10.dp,
                sheetContent = {

                    ExpandedCommentScreen(
                        lifecycleScope = lifecycleScope,
                        navController = navController,
                        viewModel = viewModel,
                        localStorage = localStorage
                    )

                },
                sheetBackgroundColor = Color.White,
                sheetPeekHeight = 0.dp

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.retangulo_topo),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            alignment = Alignment.TopEnd
                        )

                        val array = UserRepositorySqlite(context).findUsers()

                        val user = array[0]

                        if (user.id.toInt() == publicationState.value?.publicacao?.usuario?.id) {
                            Row(
                                modifier = Modifier
                                    .width(370.dp)
                                    .padding(top = 15.dp, start = 15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.arrow_back),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clickable {
                                            navController.popBackStack()
                                        }
                                )

                                ModalEditDeletePublication(
                                    lifecycleScope,
                                    localStorage,
                                    navController
                                )

                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .width(370.dp)
                                    .padding(top = 15.dp, start = 15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.arrow_back),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clickable {
                                            navController.popBackStack()
                                        }
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    if (publicationState.value == null) {
                        isLoading = true
                        ProgressBar(isDisplayed = isLoading)
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(Color(168, 155, 255, 102))
                            ) {
                                AsyncImage(
                                    model = publicationState.value?.publicacao?.usuario?.foto.orEmpty(),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 5.dp, end = 2.dp)
                                        .clip(shape = RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Text(
                                text = publicationState.value?.publicacao?.usuario?.nome.orEmpty(),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .width(170.dp)
                                    .height(55.dp)
                                    .clickable {
                                        val array = UserRepositorySqlite(context).findUsers()

                                        val user = array[0]

                                        var id = publicationState.value?.publicacao?.usuario?.id

                                        Log.e("id_usuario", "id_usuario pra tela: $id",)

                                        if (user.id.toInt() == id) {
                                            navController.navigate("profile")
                                        } else {
                                            localStorage.salvarValor(
                                                context,
                                                id.toString(),
                                                "idUsuario"
                                            )
                                            navController.navigate("profileViewed")
                                        }
                                    },
                                fontSize = 18.sp,
                                color = Contraste
                            )

                            GradientButtonSmall(
                                onClick = {},
                                text = stringResource(id = R.string.botao_recomendar),
                                color1 = Destaque1,
                                color2 = Destaque2
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            //localStorage.salvarValor(context, publicationState.value?.publicacao?.tags.toString(), "tagsPublicação")

                            publicationState.value?.publicacao?.tags?.take(2)?.forEach { tag ->
                                GradientButtonTag(
                                    onClick = { /*TODO*/ },
                                    color1 = Destaque1,
                                    color2 = Destaque2,
                                    tagId = tag.id,
                                    text = tag.nome_tag,
                                    textColor = Color(168, 155, 255, 255)
                                )
                            }

                            if ((publicationState.value?.publicacao?.tags?.size ?: 0) > 1) {
                                ModalTagsPublication(
                                    color1 = Destaque1,
                                    color2 = Destaque2,
                                    viewModel
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        LazyRow() {
                            items(publicationState.value?.publicacao?.anexos.orEmpty()) { publication ->
                                Card(
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(270.dp)
                                        .padding(start = 16.dp, 2.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable {

                                        },
                                    elevation = 20.dp
                                ) {
                                    Column(
                                        modifier = Modifier.height(100.dp),
                                        verticalArrangement = Arrangement.Top
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .height(250.dp)
                                                .fillMaxWidth()
                                                .background(
                                                    Color(168, 155, 255, 102),
                                                    shape = RoundedCornerShape(16.dp)
                                                )
                                        ) {
                                            AsyncImage(
                                                model = publication.anexo,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .width(225.dp)
                                                    .height(240.dp)
                                                    .clip(shape = RoundedCornerShape(10.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GradientButtonSmall(
                                onClick = {
                                    val fotoAnunciante = viewModelId.foto_perfil
                                    val nomeAnunciante = viewModelId.nome_perfil
                                    val idAnunciante = viewModelId.id_perfil
                                    val jsonUser1 = UserChat(
                                        id = dadaUser[0].id.toInt(),
                                        nome = dadaUser[0].nome,
                                        foto = fotoDoCara
                                    )

                                    Log.w("idmeu", "id meu: ${dadaUser[0].id}", )

                                    val jsonUserAnunciante = UserChat(
                                        id = idAnunciante.toInt(),
                                        foto = fotoAnunciante,
                                        nome = nomeAnunciante
                                    )

                                    listUsuario = listUsuario + jsonUser1

                                    listUsuario = listUsuario + jsonUserAnunciante


                                    val jsonBody = JsonObject().apply {
                                        val usersArray = JsonArray()

                                        for (user in listUsuario) {
                                            val userObject = JsonObject().apply {
                                                addProperty("id", user.id)
                                                addProperty("nome", user.nome)
                                                addProperty("foto", user.foto)
                                            }
                                            usersArray.add(userObject)
                                        }

                                        add("users", usersArray)
                                        //addProperty("status", true)
                                    }

                                    Log.w("corpo", "corpo: $jsonBody", )

//                                    socket.connect()
//                                    if (socket.connected()) {
//                                        Log.d("SocketIO", "Conexão estabelecida com sucesso")
//                                    } else {
//                                        Log.e("SocketIO", "Falha ao estabelecer a conexão")
//                                    }

                                    socket.emit("createRoom", jsonBody)

                                    // Ouça o evento do socket
                                    socket.on("newChat") { args ->
                                        Log.e("entrou", "foi")
                                        args.let { d ->
                                            if (d.isNotEmpty()) {
                                                val data = d[0]

                                                Log.e("Data", "$data")
                                                if (data.toString().isNotEmpty()) {
                                                    val chat =
                                                        Gson().fromJson(data.toString(), MensagensResponse::class.java)
                                                    Log.e("Chat - Luizão", "$chat", )
                                                    newChat = chat
                                                    Log.e("mumu aquiiii", "AnnouceDetail: ${chat}")
                                                    Log.e("mumu testando dentro", "${ newChat.id_chat}", )
                                                    chatViewModel.idChat = newChat.id_chat
                                                }else{
                                                    Log.e("Morreu", "Bateu aqui: ${data.toString().isNotEmpty()}", )
                                                }
                                            }
                                        }
                                    }


                                    Log.e("luiz testando fora", "${ newChat.id_chat}", )
                                    chatViewModel.idUser2 = idAnunciante.toInt()
                                    Log.w("idUser2", "aloka: ${chatViewModel.idUser2}", )
                                    chatViewModel.foto = fotoAnunciante
                                    Log.w("foto", "aloka: ${chatViewModel.foto}", )
                                    chatViewModel.nome = nomeAnunciante
                                    Log.w("nome", "aloka: ${chatViewModel.nome}", )

                                    navController.navigate("chat")
                                },
                                text = stringResource(id = R.string.botao_responder),
                                color1 = Destaque1,
                                color2 = Destaque2
                            )

                            ButtonGivePoint(
                                onClick = {
                                    lifecycleScope.launch {

                                        isClicked = !isClicked
                                        Log.i("isclicked", "ExpandedPublicationScreen: ${isClicked}")
                                        if (isClicked) {
                                            postGivePoint()
                                        } else {
                                            postRemovePoint()
                                        }
                                    }
                                },
                                text = stringResource(id = R.string.text_give_point),
                                btnColor = btnColor,
                                textColor = textColor

                            )

                            GradientButtonSmall(
                                onClick = {
                                    scope.launch {
                                        if (sheetState.isCollapsed) {
                                            sheetState.expand()
                                            localStorage.salvarValor(
                                                context,
                                                id.toString(),
                                                "id_publicacao"
                                            )
                                        } else {
                                            sheetState.collapse()
                                        }
                                    }
                                },
                                text = stringResource(id = R.string.botao_comentarios),
                                color1 = Destaque1,
                                color2 = Destaque2
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = publicationState.value?.publicacao?.titulo.orEmpty(),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .padding(start = 16.dp, end = 10.dp),
                                fontSize = 20.sp,
                                color = Contraste,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = publicationState.value?.publicacao?.descricao.orEmpty(),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .width(430.dp)
                                    .height(65.dp)
                                    .padding(start = 16.dp, end = 10.dp),
                                fontSize = 15.sp,
                                color = Contraste
                            )
                        }
                    }
                }
            }
        }
    }
}