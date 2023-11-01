package br.senai.sp.jandira.costurie_app.screens.expandedPublication

import android.util.Log
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
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import br.senai.sp.jandira.costurie_app.function.deleteUserSQLite
import br.senai.sp.jandira.costurie_app.model.BaseResponseIdPublication
import br.senai.sp.jandira.costurie_app.model.PublicationGetResponse
import br.senai.sp.jandira.costurie_app.model.UsersTagResponse
import br.senai.sp.jandira.costurie_app.repository.PublicationRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.viewModel.TagPublicationViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserTagViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun ExpandedPublicationScreen(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavController,
    viewModel: TagPublicationViewModel,
    localStorage: Storage,
) {
    var context = LocalContext.current

    var id = localStorage.lerValor(context, "id_publicacao")

    val publicationState = remember { mutableStateOf<BaseResponseIdPublication?>(null) }

    val isOpen = remember { mutableStateOf(false) }

    // Chame essa função para abrir a modal
    fun openModal() {
        isOpen.value = true
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
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("EDICAO DE PERFIL", "updateUser: $errorBody")
        }
    }

    LaunchedEffect(key1 = true) {

        getPublicationById()

        Log.e("PUBLICAÇÃO", "A tal da publication explodida: ${getPublicationById()}")
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

                                ModalEditDeletePublication(lifecycleScope, localStorage, navController)

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
                            .height(45.dp)
                            .clickable {
                                var id = publicationState.value?.publicacao?.usuario?.id
                                localStorage.salvarValor(context, id.toString(), "idUsuario")
                                navController.navigate("profileViewed")
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
                        ModalTagsPublication(color1 = Destaque1, color2 = Destaque2, viewModel)
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
                        onClick = { /*TODO*/ },
                        text = stringResource(id = R.string.botao_responder),
                        color1 = Destaque1,
                        color2 = Destaque2
                    )

                    ButtonGivePoint(
                        onClick = { /*TODO*/ },
                        text = "DAR PONTO"
                    )

                    GradientButtonSmall(
                        onClick = {
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