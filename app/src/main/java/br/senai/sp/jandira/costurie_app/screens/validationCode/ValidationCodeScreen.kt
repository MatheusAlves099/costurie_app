package br.senai.sp.jandira.costurie_app.screens.validationCode

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import br.senai.sp.jandira.costurie_app.components.OtpTextField
import br.senai.sp.jandira.costurie_app.repository.PasswordResetRepository
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import kotlinx.coroutines.launch

//navController: NavController
@Composable
fun ValidationCodeScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: PasswordResetViewModel
) {

    val idUser = viewModel.id

    Log.e("Testando", "ValidationCodeScreen: $idUser")

    var otpValue by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    fun token(
        id: Int,
        otpValue: String,
        viewModel: PasswordResetViewModel
    ) {
        val resetPasswordRepository = PasswordResetRepository()
        lifecycleScope.launch {
            val response = resetPasswordRepository.validateResetCode(id, otpValue)

            Log.e("boi", "ValidateResetCode: $response")

            if (response.isSuccessful) {
                Log.e(MainActivity::class.java.simpleName, "Token bem-sucedido")
                Log.e("Token", "Token: ${response.body()}")
                val checagem = response.body()?.get("status")
                if (checagem.toString() == "401") {
                    Toast.makeText(
                        context,
                        "O token encaminhado na requisicão não está valido",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(context, "Token validado", Toast.LENGTH_SHORT).show()
//                    val id = response.body()?.get("id")?.asInt
//                    viewModel.id = id
                    navController.navigate("tradePassword")
                }
            } else {
                val errorBody = response.errorBody()?.string()

                Log.e(
                    MainActivity::class.java.simpleName,
                    "Erro durante o reset da senha: $errorBody"
                )
                Toast.makeText(context, "Token inválido", Toast.LENGTH_SHORT).show()
            }
        }

    }

    Costurie_appTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
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
                        .height(250.dp),
                    alignment = Alignment.TopStart

                )
                Image(
                    painter = painterResource(id = R.drawable.forma_baixo_recuperar_a_senha),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.BottomEnd
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.modal_validar_codigo2),
                        contentDescription = "",
                        alignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(100.dp))

                        Button(
                            onClick = {
                                navController.navigate("password")
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
                            modifier = Modifier.height(125.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                color = Color.White,
                                text = stringResource(R.string.validacao_de_codigo).uppercase(),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(125.dp))
                        Column() {
                            Text(
                                color = Contraste,
                                text = stringResource(id = R.string.codigo_de_verificacao),
                                modifier = Modifier
                                    .width(247.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OtpTextField(
                                otpText = otpValue,
                                onOtpTextChange = { value, otpInputFilled ->
                                    otpValue = value
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(60.dp))
                        GradientButton(
                            onClick = {
                                if (idUser != null) {
                                    token(idUser, otpValue, viewModel)
                                }
                            },
                            text = stringResource(id = R.string.texto_botao_confirmar).uppercase(),
                            color1 = Destaque1,
                            color2 = Destaque2
                        )
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun ValidationCodeScreenPreview() {
//    ValidationCodeScreen(navController = NavController())
//}