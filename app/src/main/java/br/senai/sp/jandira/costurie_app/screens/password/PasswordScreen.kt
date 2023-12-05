package br.senai.sp.jandira.costurie_app.screens.password

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import br.senai.sp.jandira.costurie_app.viewModel.PasswordResetViewModel
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.components.GradientButton
import br.senai.sp.jandira.costurie_app.components.ModalPasswordSucess
import br.senai.sp.jandira.costurie_app.repository.PasswordResetRepository
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Principal2
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: PasswordResetViewModel
) {
    var textstate2 by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var validateEmail by rememberSaveable {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    fun validateData(email: String): Boolean {

        validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        return validateEmail
    }

    fun resetPassword(
        email: String,
        viewModel: PasswordResetViewModel
    ) {
        if (validateData(email)) {
            val resetPasswordRepository = PasswordResetRepository()
            lifecycleScope.launch {
                val response = resetPasswordRepository.requestPasswordReset(email)

                Log.e("boi", "resetPassword: $response")

                if (response.isSuccessful) {
                    Log.e(MainActivity::class.java.simpleName, "Email bem-sucedido")
                    Log.e("Email", "Email: ${response.body()}")
                    val checagem = response.body()?.get("status")
                    if (checagem.toString() == "404") {
                        Toast.makeText(context, "O email não foi encontrado em nosso sistema", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Token enviado ao Email", Toast.LENGTH_SHORT).show()
                        val id = response.body()?.get("id")?.asInt
                        viewModel.id = id
                        navController.navigate("validationCode")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()

                    Log.e(
                        MainActivity::class.java.simpleName,
                        "Erro durante o reset da senha: $errorBody"
                    )
                    Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Por favor, reolhe suas caixas de texto", Toast.LENGTH_SHORT)
        }
    }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.forma_topo_recuperar_a_senha),
                    contentDescription = "",
                    modifier = Modifier
                        .width(177.dp)
                        .height(272.dp),
                    alignment = Alignment.TopStart

                )
                Image(
                    painter = painterResource(id = R.drawable.forma_baixo_recuperar_a_senha),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.BottomEnd
                )
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.modal_redefinir_senha),
                        contentDescription = "",
                        alignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(125.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                      navController.navigate("login")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.arrow_back),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(35.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.height(500.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                color = Color.White,
                                text = stringResource(R.string.recuperar_senha).uppercase(),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.costureira2),
                                contentDescription = "",
                                alignment = Alignment.Center,
                                modifier = Modifier.width(200.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.verificacao_email),
                                modifier = Modifier
                                    .width(247.dp),
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                            OutlinedTextField(
                                value = email,
                                onValueChange = { newEmail ->
                                    email = newEmail
                                },
                                label = {
                                    Text(
                                        stringResource(id = R.string.email_label),
                                        fontSize = 15.sp
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    unfocusedLabelColor = Color.Black,
                                    cursorColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    textColor = Color.Black,
                                    containerColor = Principal2,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .shadow(20.dp, shape = RoundedCornerShape(20.dp))
                                    .height(60.dp),
                                textStyle = TextStyle.Default.copy(fontSize = 15.sp)
                            )

                            ModalPasswordSucess(
                                navController = navController,
                                text = stringResource(id = R.string.text_modal_send_code_sucess)
                            )
                        }
                    }
                }
            }
        }
    }
}