package br.senai.sp.jandira.costurie_app.components
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.service.chat.ChatClient
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ChatViewModel
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MessageBar(
    value: String,
    chatViewModel: ChatViewModel,
    onValueChange: (String) -> Unit,
    client: ChatClient,
    navController: NavController,
    idUsuario: Int
) {
    var context = LocalContext.current

    var mensagemState by remember {
        mutableStateOf(value)
    }

    var fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var nome = chatViewModel.nome
    val idChat = chatViewModel.idChat
    val idUser2 = chatViewModel.idUser2

    val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("chat")

    TextField(
        value = mensagemState,
        onValueChange = {
            mensagemState = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .border(
                BorderStroke(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))
                ),
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
            ),
        trailingIcon = {
            Row (
                modifier = Modifier
                    .padding(end = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_add_image),
                    contentDescription = "",
                    Modifier
                        .size(24.dp)
                        .clickable {
//                            launcherImage()
                        }
                )
                
                Spacer(modifier = Modifier.width(6.dp))
                
                Icon(
                    painter = painterResource(id = R.drawable.send_icon), // Altere para o ícone desejado para a resposta,
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onValueChange(mensagemState)
                            mensagemState = ""
                        },
                    tint = Destaque2
                )
            }

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
                text = mensagemState,
                fontSize = 18.sp,
                color = Contraste2,
                maxLines = 1
            )
        },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black)
    )
}
//        @Composable
//        fun launcherImage() {
//            val launcher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.GetContent()
//            ) { uri ->
//                uri?.let {
//                    fotoUri = it
//
//                    val storageRefChild = storageRef.child("${System.currentTimeMillis()}_${fotoUri!!.lastPathSegment}")
//                    val uploadTask = storageRefChild.putFile(fotoUri!!)
//
//                    uploadTask.addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            storageRefChild.downloadUrl.addOnSuccessListener { downloadUri ->
//                                // Agora você pode usar downloadUri para obter a URL da imagem no Firebase Storage
//                                val imageUrl = downloadUri.toString()
//
//                                val json = JSONObject().apply {
//                                    put("messageBy", idUsuario)
//                                    put("messageTo", idUser2)
//                                    put("message", "")
//                                    put("image", imageUrl)
//                                    put("chatId", idChat)
//                                }
//
//                                client.sendMessage(json)
//                            }
//                        } else {
//                            Log.e("PictureScreen", "Error uploading image to Firebase Storage: ${task.exception}")
//                        }
//                    }
//                }
//            }
//        }
