package br.senai.sp.jandira.costurie_app.screens.personalization


import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.WhiteButton
import br.senai.sp.jandira.costurie_app.components.WhiteButtonSmall
import br.senai.sp.jandira.costurie_app.repository.UserRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

@Composable
fun ProfilePicScreen(navController: NavController, localStorage: Storage, lifecycleScope: LifecycleCoroutineScope) {

    val brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))

    //REFERENCIA PARA ACESSO E MANiPULACAO DO CLOUD STORAGE
    var storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("images")

    //REFERENCIA PARA ACESSO E MANIPULACAO DO CLOUD FIRESTORE
    var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var context = LocalContext.current

    //obter fot oda galeria de imagens

    var fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    //criar o objeto que abrira a galeria e retornara a uri da imagem selecionada
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        fotoUri = it
    }

    var painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(fotoUri).build()
    )

    val array = UserRepositorySqlite(context).findUsers()

    val user = array[0]

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
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.navigate("name")
                        },

                        ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                        )
                    }
                    Button(
                        onClick = {
                            fotoUri?.let {
                                storageRef.putFile(it).addOnCompleteListener { task->

                                    if (task.isSuccessful) {

                                        storageRef.downloadUrl.addOnSuccessListener { uri ->

                                            val map = HashMap<String, Any>()
                                            map["pic"] = uri.toString()

                                            firebaseFirestore.collection("images").add(map).addOnCompleteListener { firestoreTask ->

                                                if (firestoreTask.isSuccessful){
                                                    Toast.makeText(context, "UPLOAD REALIZADO COM SUCESSO", Toast.LENGTH_SHORT).show()
                                                }else{
                                                    Toast.makeText(context, "ERRO AO TENTAR REALIZAR O UPLOAD", Toast.LENGTH_SHORT).show()
                                                }

                                                localStorage.salvarValor(context, uri.toString(), "foto")
                                                //BARRA DE PROGRESSO DO UPLOAD
                                            }
                                        }

                                    }else{

                                        Toast.makeText(context,  "ERRO AO TENTAR REALIZAR O UPLOAD", Toast.LENGTH_SHORT).show()

                                    }
                                }
                            }


                            Log.i("localstorage", "${localStorage.lerValor(context, "foto")}")
                            Log.i("localstorage", "${localStorage.lerValor(context, "nome")}")
                            navController.navigate("description")

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
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }
                }//row
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(

                        text = stringResource(id = R.string.texto_foto_de_perfil).uppercase(),
                        modifier = Modifier.height(30.dp),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(id = R.string.descricao_foto_de_perfil1),
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light
                    )
                }//column

                Text(
                    text = stringResource(id = R.string.descricao_foto_de_perfil2),
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )//text

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        shape = CircleShape,
                        color = Color.Transparent
                    ) {

                        AsyncImage(
                            model = user.foto,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.profile_default)
                        )
                        Image(
                            painter = painter,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    }
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_camera_alt_24),
                        contentDescription = "",
                        modifier = Modifier
                            .height(28.dp)
                            .width(48.dp)
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    WhiteButtonSmall(
                        onClick = {
                            navController.navigate("description")
                        },
                        text = "Pular".uppercase()
                    )
                }

            }
        }
    }
}

//@Preview (showSystemUi = true, showBackground = true)
//@Composable
//fun PreviewProfilePicScreen() {
//    ProfilePicScreen()
//}