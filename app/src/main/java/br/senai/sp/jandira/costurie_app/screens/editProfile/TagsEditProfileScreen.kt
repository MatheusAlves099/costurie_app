package br.senai.sp.jandira.costurie_app.screens.editProfile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.GradientButtonTag
import br.senai.sp.jandira.costurie_app.components.TagColorViewModel
import br.senai.sp.jandira.costurie_app.model.TagEditResponse
import br.senai.sp.jandira.costurie_app.model.TagResponse
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.repository.TagsRepository
import br.senai.sp.jandira.costurie_app.repository.UserRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.viewModel.TagsViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsEditProfileScreen(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavController,
    viewModelUser: UserViewModel,
    viewModelTags: TagsViewModel,
    localStorage: Storage
) {

    var context = LocalContext.current

    var id = localStorage.lerValor(context, "id")
    var nome = localStorage.lerValor(context, "nome")
    var estado = localStorage.lerValor(context, "estado")
    var bairro = localStorage.lerValor(context, "bairro")
    var cidade = localStorage.lerValor(context, "cidade")
    var descricao = localStorage.lerValor(context, "descricao")
    var foto = localStorage.lerValor(context, "foto")
    Log.w("Chega", "vendo se os dados: $id, $nome, $foto ")
    val fotoUri: Uri? = Uri.parse(foto)
    Log.w("Chega", "vendo se os dados: $fotoUri ")
    var id_localizacao = localStorage.lerValor(context, "id_localizacao")
    var token = localStorage.lerValor(context, "token")
    var nome_de_usuario = localStorage.lerValor(context, "nome_de_usuario")

    Log.w("Chega", "vendo se os dados: $id, $nome, $nome_de_usuario, $estado, $bairro, $cidade, $descricao, $fotoUri, $id_localizacao, $token", )

    val scrollState = rememberScrollState()

    fun updateUser(
        id_usuario: Int,
        token: String,
        viewModel: UserViewModel,
        id_localizacao: Int,
        cidade: String,
        estado: String,
        bairro: String,
        nome: String,
        descricao: String,
        foto: String?,
        nome_de_usuario: String,
        tags: MutableList<TagResponseId>
    ) {
        val userRepository = UserRepository()

        lifecycleScope.launch {
            Log.e("ID", "user: $id_usuario")
            Log.e("token", "user: $token")

            val array = UserRepositorySqlite(context).findUsers()

            val user = array[0]

            val response = userRepository.updateUser(
                user.id.toInt(),
                user.token,
                id_localizacao,
                cidade,
                estado,
                bairro,
                nome,
                descricao,
                foto,
                nome_de_usuario,
                tags
            )

            Log.e("TAG4", "user: $response")
            Log.i("TAG5", "user: ${response.body()}")

            if (response.isSuccessful) {
                Log.e(MainActivity::class.java.simpleName, "Usuário atualizado com sucesso")
                Log.e("user", "user: ${response.body()} ")

                viewModel.setProfileEditSuccess(true)

                navController.navigate("profile")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("EDICAO DE PERFIL", "updateUser: $errorBody")
                val descricao = response.body()?.descricao
                if (descricao != null) {
                    if (descricao.length > 255)
                        Log.e(
                            MainActivity::class.java.simpleName,
                            "Erro durante a atualização dos dados do usuário: ${errorBody}"
                        )
                    Toast.makeText(
                        context,
                        "Descricão grande demais",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Toast.makeText(
                    context,
                    "Erro durante a atualização dos dados do usuário: $errorBody",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    var tagsList by remember() {
        mutableStateOf(listOf<TagEditResponse>())
    }

    var pesquisaState by remember {
        mutableStateOf("")
    }

    val viewModel: TagColorViewModel = viewModel()

    var tagsArray = mutableListOf<TagResponseId>()

    val tagsRepository = TagsRepository()

    val array = UserRepositorySqlite(context).findUsers()

    val user = array[0]

    fun filtro(text: String): List<TagEditResponse> {
        lifecycleScope.launch {
            tagsList = tagsRepository.getAllTags2(user.token).body()!!.data
        }
        var newList: List<TagEditResponse> = tagsList.filter {
            it.nome_tag.contains(text, ignoreCase = true)
        }
        return newList
    }

    var isClicked by remember {
        mutableStateOf(false)
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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                //.padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(20.dp)
                        .border(
                            shape = RoundedCornerShape(24.dp),
                            width = 1.dp, // Espessura da borda
                            color = Color.Black, // Cor da borda
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(45.dp)
//                                    .padding(15.dp)
                                    .clickable {
                                        navController.popBackStack()
                                    },
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.save_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(35.dp)
                                    //.padding(15.dp)
                                    .clickable {
                                        if (tagsArray.isEmpty()) {
                                            Toast.makeText(context, "É necessário escolher ao menos uma tag!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            lifecycleScope.launch {
                                                updateUser(
                                                    id_usuario = user.id.toInt(),
                                                    token = user.token,
                                                    viewModelUser,
                                                    id_localizacao = id_localizacao!!.toInt(),
                                                    bairro = bairro!!,
                                                    cidade = cidade!!,
                                                    estado = estado!!,
                                                    descricao = descricao!!,
                                                    foto = localStorage.lerValor(context, "foto"),
                                                    nome_de_usuario = nome_de_usuario!!,
                                                    nome = nome!!,
                                                    tags = tagsArray
                                                )
                                            }
                                        }
                                    },
                                colorFilter = ColorFilter.tint(Color(0xFFC98FEC))
                            )
                        }
                        Text(
                            color = Color.Black,
                            text = "Você pode escolher uma tag para seu perfil",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.width(310.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Log.i(
                                "TAGSjhkgvsdakjgh",
                                "Dados em viewModelUser.tags: ${viewModelTags.tags}"
                            )


                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                                state = rememberLazyGridState(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(filtro(pesquisaState)) {
                                    GradientButtonTag(
                                        onClick = {
                                            isClicked = !isClicked
                                            if (isClicked) {
                                                viewModel.setTagColor(it.id, Destaque1, Destaque2)
                                                viewModel.setTagTextColor(it.id, Color.White, Color.White)
                                                if (!tagsArray.contains(TagResponseId(it.id))){
                                                    tagsArray.add(TagResponseId(it.id))
                                                }
                                            } else {
                                                viewModel.setTagColor(it.id, Color.Transparent, Color.Transparent)
                                                viewModel.setTagTextColor(it.id, Destaque1, Destaque2)
                                                if (tagsArray.contains(TagResponseId(it.id))) {
                                                    tagsArray.remove(TagResponseId(it.id))
                                                }
                                            }

                                            Log.e("it.nome", "mometag: ${it.nome_tag}", )
                                            Log.e("it.id", "id: ${it.id}", )
                                            Log.e("array", "array: ${tagsArray}", )
                                        },
                                        tagId = it.id,
                                        color1 = Destaque1,
                                        color2 = Destaque2,
                                        text = it.nome_tag,
                                        textColor = Color(168, 155, 255, 255),
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











