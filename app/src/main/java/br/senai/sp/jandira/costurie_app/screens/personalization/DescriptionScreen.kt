package br.senai.sp.jandira.costurie_app.screens.personalization

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.WhiteButton
import br.senai.sp.jandira.costurie_app.components.WhiteButtonSmall
import br.senai.sp.jandira.costurie_app.repository.UserRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import kotlinx.coroutines.launch
import retrofit2.http.Url

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionScreen(
    navController: NavController,
    localStorage: Storage,
    lifecycleScope: LifecycleCoroutineScope
) {

    val brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))
    var descriptionState by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val userRepository = UserRepository()

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
                    .fillMaxSize()
                    .padding(15.dp),
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
                            navController.navigate("foto")
                        },

                        ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(35.dp)
                        )
                    }
                    Button(
                        onClick = {
                            if (!descriptionState.isEmpty()) {
                                localStorage.salvarValor(context, descriptionState, "descricao")

                                Log.i("localstorage", "${localStorage.lerValor(context, "foto")}")
                                Log.i("localstorage", "${localStorage.lerValor(context, "nome")}")
                                Log.i("localstorage", "${localStorage.lerValor(context, "descricao")}")

                                lifecycleScope.launch {
                                    userRepository.updateUserNamePicDesc(
                                        id = user.id.toInt(),
                                        nome = localStorage.lerValor(context, "nome") ?: "nao foi",
                                        descricao = localStorage.lerValor(context, "descricao") ?: "nao foi",
                                        foto = localStorage.lerValor(context, "foto")
                                    )
                                }
//                                updateUser(
//                                    nome = localStorage.lerValor(context, "nome") ?: "",
//                                    descricao = localStorage.lerValor(context, "descricao") ?: "",
//                                    foto = Uri.parse(localStorage.lerValor(context, "foto"))
//                                )

                                Log.i("teste", "${
                                    lifecycleScope.launch {
                                        UserRepository().getUser(
                                            user.id.toInt(), user.token
                                        )
                                    }
                                }")
                                navController.navigate("location")
                            } else {
                                Toast.makeText(
                                    context,
                                    "Erro: preencha o campo",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            lifecycleScope.launch {
                                var costureira = userRepository.getUser(
                                    user.id.toInt(), user.token
                                )
                                Log.i("usuario", "${costureira.body()}")
                            }
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
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.texto_descricao_do_seu_perfil).uppercase(),
                        modifier = Modifier.height(30.dp),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    //Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text =
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Black)) {
                                append(stringResource(id = R.string.descricao_da_descricao_do_seu_perfil))
                            }

                        },
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = stringResource(id = R.string.texto_adicionar_descricao),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                //Spacer(modifier = Modifier.height(50.dp))

                CustomOutlinedTextField2(
                    value = descriptionState,
                    onValueChange = {
                        descriptionState = it
                    },
                    label = stringResource(id = R.string.descricao_do_perfil_label),
                    borderColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 35.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    WhiteButton(
                        onClick = {
                            localStorage.salvarValor(context, descriptionState, "descricao")
                            navController.navigate("location")
                        },
                        text = "Pular".uppercase()
                    )
                }

            }

        }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun PreviewDescriptionScreen() {
//    DescriptionScreen()
//}