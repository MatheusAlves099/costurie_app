package br.senai.sp.jandira.costurie_app.screens.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.ModalFilter
import br.senai.sp.jandira.costurie_app.components.ModalLocation
import br.senai.sp.jandira.costurie_app.model.TagsResponse
import br.senai.sp.jandira.costurie_app.model.UsersTagResponse
import br.senai.sp.jandira.costurie_app.repository.TagsRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.viewModel.UserTagViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.lang.reflect.Type

@Composable
fun ProfileListScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: UserViewModel,
    profiles: List<UsersTagResponse>,
    viewModelUserTags: UserTagViewModel,
    localStorage: Storage
) {

    var context = LocalContext.current

    var newList by remember {
        mutableStateOf(listOf<UsersTagResponse>())
    }

    var isDialogOpen by remember { mutableStateOf(false) }

    var pesquisaState by remember {
        mutableStateOf("")
    }

    var listUsersTag by remember {
        mutableStateOf(listOf<UsersTagResponse>())
    }

    var fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var id = localStorage.lerValor(context, "idSelecionado")
    var nome = localStorage.lerValor(context, "tagSelecionada")

    val idTag = viewModelUserTags.id
    val tagSelecionada = viewModelUserTags.nome
    Log.w("TAG", "ProfileListScreen: $idTag, $tagSelecionada")

    fun filtro(text: String): List<UsersTagResponse> {
        var newList: List<UsersTagResponse> = listUsersTag.filter {
            it.nome.contains(text, ignoreCase = true)
        }
        return newList
    }

    suspend fun getUsersByTag(
        token: String,
        id_tag: Int,
        nome_tag: String
    ): List<UsersTagResponse> {
        val tagRepository = TagsRepository()

        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = tagRepository.getUserByTag(user.token, id_tag, nome_tag)

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                val usersTagJson = responseBody.getAsJsonArray("usuarios")
                val type: Type = object : TypeToken<List<UsersTagResponse>>() {}.type
                val usersTagList: List<UsersTagResponse> = Gson().fromJson(usersTagJson, type)

                // Log para verificar as tags recebidas
                Log.e(MainActivity::class.java.simpleName, "usuarios com suas tags recebidas")
                Log.e("user", "user: $usersTagList")

                return usersTagList
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("TODAS AS TAGS", "Tags: $errorBody")
            Toast.makeText(
                context,
                "Não temos costureiras com essa tag no momento. Desculpe pelo transtorno!",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Se algo deu errado ou não há tags, retorne uma lista vazia
        return emptyList()
    }

    LaunchedEffect(Unit) {
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val tags = getUsersByTag(user.token, id!!.toInt(), nome!!).toMutableList()

        listUsersTag = tags

        Log.d("TAGS", "Número de usuarios recebidos: ${tags.size}")

    }

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
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

                        CustomOutlinedTextField2(
                            value = pesquisaState,
                            onValueChange = {
                                pesquisaState = it
                                filtro(pesquisaState)
                            },
                            label = stringResource(id = R.string.lista_de_perfis_textfield),
                            borderColor = Color.Transparent,
                            modifier = Modifier
                                .width(260.dp)
                                .height(62.dp)
                        )

                        newList = ModalLocation(lifecycleScope, localStorage, listUsersTag)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        if (filtro(pesquisaState).isEmpty()) {
                            if (newList.isEmpty()) {
                                listUsersTag
                            } else {
                                newList
                            }
                        } else {
                            filtro(pesquisaState)
                        }
                    ) { profile ->
                        Card(
                            modifier = Modifier
                                .size(380.dp, 85.dp)
                                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                                .clickable {
                                    var id = profile.id
                                    localStorage.salvarValor(context, id.toString(), "idUsuario")

                                    navController.navigate("profileViewed")
                                },
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(15.dp),
                            elevation = AppBarDefaults.TopAppBarElevation
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
                                        model = profile.foto,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(bottom = 5.dp, end = 2.dp)
                                            .clip(shape = RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Text(
                                    text = profile.nome,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(25.dp),
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
}

