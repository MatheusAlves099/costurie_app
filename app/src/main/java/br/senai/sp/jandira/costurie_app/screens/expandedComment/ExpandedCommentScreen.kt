package br.senai.sp.jandira.costurie_app.screens.expandedComment

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextFieldComment
import br.senai.sp.jandira.costurie_app.model.BaseCommentResponse
import br.senai.sp.jandira.costurie_app.model.CommentResponse
import br.senai.sp.jandira.costurie_app.model.ReplyCommentGetResponse
import br.senai.sp.jandira.costurie_app.repository.CommentRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.viewModel.TagPublicationViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ExpandedCommentScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: TagPublicationViewModel,
    localStorage: Storage
) {
    var context = LocalContext.current

    val commentState = remember { mutableStateOf(emptyList<CommentResponse>()) }

    val replyCommentState = remember { mutableStateOf(emptyList<ReplyCommentGetResponse>()) }



    var shouldUpdateComments by remember { mutableStateOf(false) }

    var isReplyMode by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    var id = localStorage.lerValor(context, "id_publicacao")

    suspend fun getCommentByPublication() {
        val commentRepository = CommentRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = commentRepository.getCommentByPublication(user.token, id!!.toInt())

        if (response.isSuccessful) {
            val comments = response.body()?.comentarios ?: emptyList()
            commentState.value = comments
            Log.i("comentatios", "getCommentByPublication: ${comments}")

            val id_comentario = localStorage.salvarValor(
                context,
                commentState.value[0].id.toString(),
                "id_comentario"
            )

        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("TODOS OS COMENTARIOS", "Erro: $errorBody")
//            Toast.makeText(
//                context,
//                "Erro ao buscar todos os comentarios: $errorBody",
//                Toast.LENGTH_SHORT
//            ).show()
        }
    }

    suspend fun deleteComment() {
        val commentRepository = CommentRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = commentRepository.deleteComment(user.token, commentState.value[0].id)

        if (response.isSuccessful) {
            Log.e(MainActivity::class.java.simpleName, "Comentario Deletado com Sucesso!")
            Log.e("comentário", "comentário: ${response.body()} ")
            Toast.makeText(
                context,
                response.body()?.get("message").toString(),
                Toast.LENGTH_SHORT
            ).show()

            shouldUpdateComments = true
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("DELETAR COMENTÁRIO", "Deletar um comentário: $errorBody")
        }
    }

    suspend fun getReplyComment() {
        val commentRepository = CommentRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = commentRepository.getReplyComment(user.token, commentState.value[0].id)

        if (response.isSuccessful) {
            val replyComments = response.body()?.resposta ?: emptyList()
            replyCommentState.value = replyComments
            Log.i("repostasComentario", "getReplyComment: $replyComments")
            Log.i("repostasComentario", "getReplyComment: $replyCommentState")

        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("TODAS AS RESPOSTAS DE COMENTARIOS", "Erro: $errorBody")
        }
    }

    LaunchedEffect(key1 = true) {
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        getCommentByPublication()

        if (commentState.value.isNotEmpty()) {
            getReplyComment()
            Log.e("PUBLICATION1", "ExploreScreen: ${getCommentByPublication()}")
            Log.e("TESTANDO", "testando: ${getReplyComment()}")
        }
    }


    Costurie_appTheme {

        var comentarioState by remember {
            mutableStateOf("")
        }

        val sheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bar_icon),
                    contentDescription = "",
                    Modifier.size(75.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, top = 60.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = "",
                    Modifier
                        .size(35.dp)
//                            .clickable { navController.popBackStack() }
                        .clickable {
                            lifecycleScope.launch {
                                getCommentByPublication()
                            }

                        }
                )
            }
        }

        if (commentState.value.isEmpty()) {
            Spacer(modifier = Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Essa publicação não há comentários",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(27.dp))
        } else {
            LazyColumn(
                modifier = Modifier
                    .height(630.dp)
            ) {
                items(commentState.value) {
                    Card(
                        modifier = Modifier
                            //.size(380.dp, if (temRespostas) 170.dp else 85.dp)
                            .size(380.dp, 85.dp)
                            .padding(start = 25.dp, top = 4.dp, bottom = 4.dp)
                            .clickable {
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
                                    model = it.usuario.foto,
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
                                    text = it.usuario.nome_de_usuario,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(18.dp),
                                    fontSize = 12.sp,
                                    color = Contraste,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = it.mensagem,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(18.dp),
                                    fontSize = 12.sp,
                                    color = Contraste
                                )

                                Text(
                                    text = "Responder",
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(25.dp)
                                        .clickable {
                                            keyboardController?.show()
                                            isReplyMode = true
                                        },
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }

                            Image(
                                painter = painterResource(id = R.drawable.trash_icon_purple),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        lifecycleScope.launch {
                                            deleteComment()
                                        }
                                    }
                            )
                        }
                    }


                }
            }
        }
        CustomOutlinedTextFieldComment(
            value = comentarioState,
            onValueChange = {
                comentarioState = it
            },
            label = stringResource(id = R.string.label_comentarios),
            localStorage,
            lifecycleScope,
            onCommentCreated = {
                shouldUpdateComments = true
            },
            isReplyMode = isReplyMode
        )


        if (shouldUpdateComments) {
            shouldUpdateComments = false
            lifecycleScope.launch {
                getCommentByPublication()
            }

        }
    }
}