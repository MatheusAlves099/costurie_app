package br.senai.sp.jandira.costurie_app.components
import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.model.CommentResponse
import br.senai.sp.jandira.costurie_app.repository.CommentRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Principal1
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomOutlinedTextFieldComment(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    localStorage: Storage,
    lifecycleScope: LifecycleCoroutineScope,
    onCommentCreated: () -> Unit,
    isReplyMode: Boolean
) {
    var context = LocalContext.current

    var id = localStorage.lerValor(context, "id_publicacao")

    var id_comentario = localStorage.lerValor(context, "id_comentario")

    val keyboardController = LocalSoftwareKeyboardController.current

    fun createComment(
        mensagem: String
    ) {
        val commentRepository = CommentRepository()
        lifecycleScope.launch {
            val array = UserRepositorySqlite(context).findUsers()

            val user = array[0]

            val response = commentRepository.postComment(
                user.token,
                id!!.toInt(),
                user.id.toInt(),
                mensagem
            )

            Log.e("COMENTARIO0", "user: $response")
            Log.i("COMENTARIO1", "user: ${response.body()}")

            if (response.isSuccessful) {

                Log.e(MainActivity::class.java.simpleName, "Comentário Feito com Sucesso!")
                Log.e("comentario", "comentario: ${response.body()} ")

                onCommentCreated()
                //navController.navigate("home")

            } else {
                val errorBody = response.errorBody()?.string()

                val checagem = response.body()
                if (checagem?.comentario!!.mensagem == "") {
                    Toast.makeText(
                        context,
                        "Campos obrigatórios não foram preenchidos.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Log.e(
                        MainActivity::class.java.simpleName,
                        "Erro durante inserir um comentario: $errorBody"
                    )
                    Toast.makeText(
                        context,
                        "Erro durante inserir um comentario",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

    }

    fun createReplyComment(
        mensagem: String
    ) {
        val commentRepository = CommentRepository()
        lifecycleScope.launch {
            val array = UserRepositorySqlite(context).findUsers()

            val user = array[0]

            val response = commentRepository.postReplyComment(
                user.token,
                user.id.toInt(),
                id_comentario!!.toInt(),
                mensagem
            )

            Log.e("RESPOSTA COMENTARIO0", "user: $response")
            Log.i("RESPOSTA COMENTARIO1", "user: ${response.body()}")

            if (response.isSuccessful) {

                Log.e(
                    MainActivity::class.java.simpleName,
                    "Resposta de Comentário Feito com Sucesso!"
                )
                Log.e("RespostaComentario", "comentario: ${response.body()} ")

                //navController.navigate("home")

            } else {
                val errorBody = response.errorBody()?.string()

                Log.e(
                    MainActivity::class.java.simpleName,
                    "Erro durante inserir uma resposta de comentario: $errorBody"
                )
                Toast.makeText(
                    context,
                    "Erro durante inserir uma resposta de comentario",
                    Toast.LENGTH_SHORT
                )
                    .show()
                val checagem = response.body()
//                if (checagem?.comentario[0].mensagem == "") {
//                    Toast.makeText(
//                        context,
//                        "Campos obrigatórios não foram preenchidos.",
//                        Toast.LENGTH_LONG
//                    ).show()
//                } else {
//                    Log.e(
//                        MainActivity::class.java.simpleName,
//                        "Erro durante inserir um comentario: $errorBody"
//                    )
//                    Toast.makeText(
//                        context,
//                        "Erro durante inserir um comentario",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
            }
        }

    }

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(
                BorderStroke(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))
                ),
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
            ),
        trailingIcon = {
            Icon(
                painter = if (isReplyMode) {
                    painterResource(id = R.drawable.send_icon) // Altere para o ícone desejado para a resposta
                } else {
                    painterResource(id = R.drawable.send_icon) // Ícone padrão
                },
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        if (isReplyMode) {
                            createReplyComment(value)
                            keyboardController?.hide()
                        } else {
                            createComment(value)
                            keyboardController?.hide()
                        }
                    },
                tint = Destaque2
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(252, 246, 255, 255),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(65, 57, 70, 255)
        ),
        shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
        placeholder = {
            Text(
                text = label,
                fontSize = 18.sp,
                color = Contraste2,
                maxLines = 1
            )
        },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black)
    )
}