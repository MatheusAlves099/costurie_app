import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.GradientButtonSmall
import br.senai.sp.jandira.costurie_app.components.GradientButtonTag
import br.senai.sp.jandira.costurie_app.components.GradientButtonTags
import br.senai.sp.jandira.costurie_app.components.ModalTags2
import br.senai.sp.jandira.costurie_app.components.ProgressBar
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.components.WhiteButtonSmall
import br.senai.sp.jandira.costurie_app.model.LocationGetResponse
import br.senai.sp.jandira.costurie_app.model.PublicationGetResponse
import br.senai.sp.jandira.costurie_app.model.TagResponse
import br.senai.sp.jandira.costurie_app.model.TagsResponse
import br.senai.sp.jandira.costurie_app.model.UserGetIDResponse
import br.senai.sp.jandira.costurie_app.repository.UserRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel2
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import org.json.JSONObject


@Composable
fun ProfileViewedScreen(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavController,
    viewModel: UserViewModel2,
    localStorage: Storage
) {

    var isModalOpen by remember { mutableStateOf(false) }

    val context = LocalContext.current
//    val focusManager = LocalFocusManager.current
//    val scrollState = rememberScrollState()
//
//    var modalTag = ModalTags2()

    var id_usuario by remember {
        mutableStateOf(0)
    }

    var isLoading = false

    var nome by remember {
        mutableStateOf("")
    }

    var descricao by remember {
        mutableStateOf("")
    }

    var nome_de_usuario by remember {
        mutableStateOf("")
    }

    var fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var id = localStorage.lerValor(context, "idUsuario")

    val profileEditSuccess = rememberUpdatedState(viewModel.profileEditSuccess.value)

    var painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(fotoUri).build()
    )

    var cidade by remember {
        mutableStateOf("")
    }

    var estado by remember {
        mutableStateOf("")
    }

    var bairro by remember {
        mutableStateOf("")
    }

    var id_localizacao by remember {
        mutableStateOf(0)
    }

    var email by remember {
        mutableStateOf("")
    }
    val userState = remember { mutableStateOf<UserGetIDResponse?>(null) }
    var publicationList by remember { mutableStateOf<List<PublicationGetResponse>>(emptyList()) }

    fun user(
        id: Int,
        token: String,
        viewModel: UserViewModel2
    ) {
        val userRepository = UserRepository()

        lifecycleScope.launch {
            Log.e("ID", "user: $id")
            Log.e("token", "user: $token")

            val response = userRepository.getUser(id, token)

            Log.e("TAG", "user: $response")
            Log.i("TAG", "user: ${response.body()}")

            if (response.isSuccessful) {
                Log.e(MainActivity::class.java.simpleName, "Usuario sucedido")
                Log.e("user", "user: ${response.body()?.usuario}")

                userState.value = response.body()

                val userResponse = response.body()

                if (userResponse != null) {
                    id_usuario = userResponse.usuario.id!!
                    nome = userResponse.usuario.nome
                    descricao = userResponse.usuario.descricao
                    nome_de_usuario = userResponse.usuario.nome_de_usuario
                    val fotoUrl = userResponse.usuario.foto
                    fotoUri = Uri.parse(fotoUrl)
                    email = userResponse.usuario.email
                    when(userResponse.localizacao){
                        is LocationGetResponse -> {
                            cidade = userResponse.localizacao.toString()
                            estado = userResponse.usuario.estado
                            bairro = userResponse.usuario.bairro
                        }
//                        is List<> -> {
//
//                        }

                    }
                    when (userResponse.usuario.id_localizacao) {

                        is Int -> {
                            id_localizacao = userResponse.usuario.id_localizacao as Int
                        }

                        else -> {

                        }
                    }

                    publicationList = userResponse.publicacoes

                    Log.d("dado", "vendo se tem dado rs: $publicationList")
                    localStorage.salvarValor(context, cidade, "cidade")
                    localStorage.salvarValor(context, estado, "estado")
                    localStorage.salvarValor(context, bairro, "bairro")

                    viewModel.id_usuario = id_usuario
                    viewModel.nome = nome
                    viewModel.descricao = descricao
                    viewModel.nome_de_usuario = nome_de_usuario
                    viewModel.foto = fotoUri
                    viewModel.email = email
                    viewModel.estados.value = listOf(estado)
                    viewModel.cidades.value = listOf(cidade)
                    viewModel.bairros.value = listOf(bairro)
                    viewModel.id_localizacao = id_localizacao

                    if (userResponse.usuario.tag.isNotEmpty()) {
                        viewModel.tags = userResponse.usuario.tag
                    } else {
                        viewModel.tags = null
                    }

                    Log.i("Thiago", "${viewModel.nome}, $fotoUri")
                } else {
                    // Lógica de tratamento caso a resposta seja nula
                }


            } else {
                val errorBody = response.errorBody()?.string()
                //val errorBody = response.body()
                Log.e(
                    MainActivity::class.java.simpleName,
                    "Erro durante pegar os dados do usuario: ${errorBody}"
                )
                Toast.makeText(
                    context,
                    "Erro durante pegar os dados do usuario",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    LaunchedEffect(key1 = true) {
        val array = UserRepositorySqlite(context).findUsers()

        val user = array[0]

        user(
            id = id!!.toInt(),
            token = user.token,
            viewModel
        )

        if (profileEditSuccess.value == true) {
            viewModel.setProfileEditSuccess(false) // Redefina o sucesso para evitar recargas repetidas
            // A edição de perfil foi bem-sucedida, recarregue os dados do usuário.
            user(id = user.id.toInt(), token = user.token, viewModel)
        }

        Log.e("TAG@", "ProfileScreen: ${user.id}, ${user.token}")
    }
    Costurie_appTheme {

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.forma_tela_perfil),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.TopStart
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 100.dp),
                //horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .height(35.dp)
                        .fillMaxWidth(),
                    Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.texto_perfil),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Image(
                        painter = painterResource(id = R.drawable.icon_chat),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {

                            },
                        alignment = Alignment.TopEnd
                    )
                }

                if (userState.value == null) {
                    isLoading = true
                    ProgressBar(isDisplayed = isLoading)
                } else {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 20.dp)
                            .width(320.dp),
                        Arrangement.Start
                    ) {

                        AsyncImage(
                            model = "$fotoUri",
                            contentDescription = "",
                            modifier = Modifier
                                .size(100.dp)
                                .border(
                                    BorderStroke(
                                        br.senai.sp.jandira.costurie_app.screens.editProfile.borderWidth,
                                        Color.White
                                    ),
                                    RoundedCornerShape(10.dp)
                                )
                                .padding(br.senai.sp.jandira.costurie_app.screens.editProfile.borderWidth)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        Column(
                        ) {
                            Text(
                                color = Color.White,
                                text = nome,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.height(28.dp)
                            )

                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                color = Color.White,
                                text = nome_de_usuario,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.height(22.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_location),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(23.dp)
                                )

                                Text(
                                    color = Color.White,
                                    text = "$cidade, $estado",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.height(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        GradientButtonSmall(
                            onClick = { /*TODO*/ },
                            text = stringResource(id = R.string.botao_recomendar),
                            color1 = Color(201, 143, 236, 255),
                            color2 = Color(168, 155, 255, 255)
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))

                        WhiteButtonSmall(
                            onClick = {

                            },
                            text = stringResource(id = R.string.botao_recomendacoes).uppercase()
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        WhiteButtonSmall(
                            onClick = {},
                            text = stringResource(id = R.string.botao_recomendados).uppercase()
                        )
                    }

                    Text(
                        color = Contraste,
                        text = descricao,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp),
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    if (viewModel.tags == null) {
                        Row(modifier = Modifier.fillMaxWidth()) {

                        }

                    } else {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .fillMaxWidth(),
                            Arrangement.SpaceEvenly
                        ) {
                            viewModel.tags?.take(2)?.forEach { tag ->
                                GradientButtonTags(
                                    onClick = {},
                                    text = tag.nome_tag,
                                    color1 = Destaque1,
                                    color2 = Destaque2,
                                )
                            }
                            if ((viewModel.tags?.size ?: 0) > 1) {
                                ModalTags2(color1 = Destaque1, color2 = Destaque2, viewModel)
                            }
                        }
                    }
                    Log.w("Lista Publicacao", "Lista Publicacao FORA: $publicationList ")
                    //Log.w("Lista Usuario", "Lista Publicacao FORA: ${userState.value?.usuario!!.publicacao} ", )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(publicationList) { publicacao ->

                            Log.w("Lista Publicacao", "Lista Publicacao DENTRO: $publicationList ")
                            Card(
                                modifier = Modifier
                                    .width(95.dp)
                                    .height(120.dp)
                                    .padding(start = 12.dp, 2.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        localStorage.salvarValor(
                                            context,
                                            publicacao.id.toString(),
                                            "id_publicacao"
                                        )
                                        navController.navigate("expandedPublication")
                                    },
                                backgroundColor = Color.White,
                                elevation = 20.dp
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(145.dp)
                                            .fillMaxWidth()
                                            .background(
                                                Color(168, 155, 255, 102),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                    ) {
                                        AsyncImage(
                                            model = publicacao.anexos[0].anexo,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(105.dp, 110.dp)
                                                .clip(shape = RoundedCornerShape(10.dp)),
                                            contentScale = ContentScale.Crop
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