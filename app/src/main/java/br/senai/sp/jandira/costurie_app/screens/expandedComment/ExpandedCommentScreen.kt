package br.senai.sp.jandira.costurie_app.screens.expandedComment

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.viewModel.TagPublicationViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ExpandedCommentScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: TagPublicationViewModel,
    localStorage: Storage
) {
    var context = LocalContext.current

    val commentState = remember { mutableStateOf(emptyList<CommentResponse>()) }

    var replyComment by remember {
        mutableStateOf(0)
    }

    val id_usuario = localStorage.lerValor(context, "idUsuario")

    //val replyCommentState = remember { mutableStateOf(emptyList<ReplyCommentGetResponse>()) }

    var shouldUpdateComments by remember { mutableStateOf(false) }

    var shouldUpdateReplyComments by remember { mutableStateOf(false) }


    var isReplyMode by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    Log.d("DEBUG", "localStorage1: $localStorage")
    var id = localStorage.lerValor(context, "id_publicacao")
    Log.d("DEBUG", "localStorage: $localStorage")

    suspend fun getCommentByPublication() {
        val commentRepository = CommentRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        Log.i("usercomment", "getCommentByPublication: ${user}")

        val response = commentRepository.getCommentByPublication(user.token, id!!.toInt())

        if (response.isSuccessful) {
            val comments = response.body()?.comentarios ?: emptyList()
            commentState.value = comments
            Log.i("comentatios", "getCommentByPublication: ${comments}")

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

    suspend fun deleteComment(commentId: Int) {
        val commentRepository = CommentRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val id_comentario = localStorage.lerValor(context, "id_comentario")

        val response = commentRepository.deleteComment(user.token, commentId)

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


    LaunchedEffect(key1 = true) {
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        getCommentByPublication()

        if (commentState.value.isNotEmpty()) {



        }
    }


    Costurie_appTheme {

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

                    val id_comentario = remember { mutableStateOf(it.id.toString()) }
                    localStorage.salvarValor(context, id_comentario.value, "id_comentario")
                    val resposta = it.respostas
                    val temRespostas = resposta.isNotEmpty()
                    Log.e("tem1", "tem respostas: $temRespostas")

                    val cardHeight = if (temRespostas) 200.dp else 85.dp
                    var isReplyTextFieldVisible by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .size(380.dp, cardHeight)
                            .padding(start = 25.dp, top = 4.dp, bottom = 4.dp)
                            .clickable {
                                // Faça algo quando o Card for clicado
                            },
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(15.dp),
                        elevation = AppBarDefaults.TopAppBarElevation
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                                Spacer(modifier = Modifier.width(7.dp))
                                Column {
                                    Text(
                                        text = it.usuario.nome_de_usuario,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .height(18.dp),
                                        fontSize = 12.sp,
                                        color = Contraste,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Text(
                                        text = it.mensagem,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .height(18.dp),
                                        fontSize = 12.sp,
                                        color = Contraste
                                    )

                                    Text(
                                        text = "Responder",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .clickable {
                                                keyboardController?.show()
                                                isReplyMode = true
                                                replyComment = it.id
                                            },
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }

                                Spacer(modifier = Modifier.width(150.dp))

                                Log.e("tem2", "tem respostas: $temRespostas")
                                val array = UserRepositorySqlite(context).findUsers()

                                val user = array[0]
                                if (it.id_usuario == user.id.toInt() || id_usuario!!.toInt() == user.id.toInt()) {
                                    Image(
                                        painter = painterResource(id = R.drawable.trash_icon_purple),
                                        contentDescription = "",
                                        modifier = Modifier
                                            //.size(25.dp)
                                            .clickable {
                                                lifecycleScope.launch {
                                                    deleteComment(it.id)
                                                }
                                            }
                                    )
                                }
                            }
                            if (temRespostas) {
                                Spacer(modifier = Modifier.height(10.dp))
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    for (respostaItem in resposta as List<ReplyCommentGetResponse>) {
                                        item {
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(shape = RoundedCornerShape(10.dp))
                                                        .background(Color(168, 155, 255, 102))
                                                ) {
                                                    AsyncImage(
                                                        model = respostaItem.usuario.foto,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(bottom = 5.dp, end = 2.dp)
                                                            .clip(shape = RoundedCornerShape(10.dp)),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(7.dp))
                                                Column {
                                                    Text(
                                                        text = respostaItem.usuario.nome_de_usuario,
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier
                                                            .width(250.dp)
                                                            .height(18.dp),
                                                        fontSize = 12.sp,
                                                        color = Contraste,
                                                        fontWeight = FontWeight.SemiBold
                                                    )

                                                    Text(
                                                        text = respostaItem.mensagem,
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier
                                                            .width(250.dp)
                                                            .height(18.dp),
                                                        fontSize = 12.sp,
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
                }
            }
        }

        CustomOutlinedTextFieldComment(
            localStorage,
            lifecycleScope,
            onCommentCreated = {
                shouldUpdateComments = true
            },
            onReplyCommentCreated = {
                shouldUpdateComments = true
            },
            isReplyMode = isReplyMode,
            idComentario = replyComment.toString()
        )


        if (shouldUpdateComments) {
            shouldUpdateComments = false
            lifecycleScope.launch {
                getCommentByPublication()
            }

        }
    }
}