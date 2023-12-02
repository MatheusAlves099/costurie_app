package br.senai.sp.jandira.costurie_app.screens.chats

import android.graphics.fonts.Font
import android.graphics.fonts.FontFamily
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import br.senai.sp.jandira.costurie_app.service.chat.ChatClient
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ChatViewModel
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Principal2
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.socket.client.Socket
import org.json.JSONObject

@Composable
fun PictureScreen(
    navController: NavController,
    chatViewModel: ChatViewModel,
    client: ChatClient,
    socket: Socket,
    idUsuario: Int,
) {
    val context = LocalContext.current

    val brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))

    var nome = chatViewModel.nome
    val idChat = chatViewModel.idChat
    val idUser2 = chatViewModel.idUser2

    var fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(fotoUri).build()
    )

    val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("chat")

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            fotoUri = it

            val storageRefChild =
                storageRef.child("${System.currentTimeMillis()}_${fotoUri!!.lastPathSegment}")
            val uploadTask = storageRefChild.putFile(fotoUri!!)

            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRefChild.downloadUrl.addOnSuccessListener { downloadUri ->
                        // Agora você pode usar downloadUri para obter a URL da imagem no Firebase Storage
                        val imageUrl = downloadUri.toString()

                        val json = JSONObject().apply {
                            put("messageBy", idUsuario)
                            put("messageTo", idUser2)
                            put("message", "")
                            put("image", imageUrl)
                            put("chatId", idChat)
                        }

                        client.sendMessage(json)
                    }
                } else {
                    Log.e(
                        "PictureScreen",
                        "Error uploading image to Firebase Storage: ${task.exception}"
                    )
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {

                                navController.popBackStack()
                            })

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Text(
                            text = "Enviar foto".uppercase(),
                            modifier = Modifier.height(30.dp),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(13.dp))

                Column(
                    modifier = Modifier
                        .width(329.dp)
                        .background(Principal2)
                        .clickable {
                            launcher.launch("image/*")
                            Log.e("foto", "$fotoUri")
                            fotoUri?.let {
//                           imagem = FirebaseMessage(imagem = fotoUri!!, context = context)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = if (fotoUri == null) {
                            Image(
                                painter = painterResource(id = R.drawable.chat_image),
                                contentDescription = "",
                                modifier = Modifier
                            )
                        } else {
                            fotoUri
                        },
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                ) {
                    Text(
                        text = "Enviar para $nome",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Destaque2,
                    )

                }

                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 28.dp),
                    horizontalAlignment = Alignment.End,

                    ) {
                    Button(
                        onClick = {
                            navController.navigate("chat")
                        },
                        modifier = Modifier
                            .size(45.dp)
                            .background(
                                brush = brush,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.send_icon_white),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}