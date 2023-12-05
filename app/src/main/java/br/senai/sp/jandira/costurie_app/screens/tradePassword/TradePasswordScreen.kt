package br.senai.sp.jandira.costurie_app.screens.tradePassword

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.viewModel.PasswordResetViewModel
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField
import br.senai.sp.jandira.costurie_app.components.GradientButton
import br.senai.sp.jandira.costurie_app.repository.PasswordResetRepository
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradePasswordScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: PasswordResetViewModel
) {
    val idUser = viewModel.id

    Log.e("Testando", "ValidationCodeScreen: $idUser")

    var passwordState by remember {
        mutableStateOf("")
    }

    var repeatPasswordState by remember {
        mutableStateOf("")
    }

    var validatePassword by rememberSaveable {
        mutableStateOf(true)
    }

    var validateConfirmPassword by rememberSaveable {
        mutableStateOf(true)
    }

    var validateArePasswordEqual by rememberSaveable {
        mutableStateOf(true)
    }

    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isConfirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val validatePasswordError = "Deve misturar letras maiúsculas e minúsculas, pelo menos um número, caracter especial e mínimo de 8 caracteres"
    val validateEqualPasswordError = "As senhas devem ser iguais"

    fun validateData(password: String, confirmPassword: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$".toRegex()

        validatePassword = passwordRegex.matches(password)
        validateConfirmPassword = passwordRegex.matches(confirmPassword)
        validateArePasswordEqual = password == confirmPassword

        return validatePassword && validateConfirmPassword && validateArePasswordEqual
    }

    fun updatePassword (
        id: Int,
        password: String,
        confirmPassword: String,
        viewModel: PasswordResetViewModel
    ) {
        if (validateData(password, confirmPassword)){
            val resetPassword = PasswordResetRepository()
            lifecycleScope.launch {
                val response = resetPassword.updatePassword(id, password)

                if (response.isSuccessful) {
                    val checagem = response.body()?.get("status")

                    if (checagem.toString() == "400") {

                        Toast.makeText(context, "Campos obrigatórios não foram preenchidos.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Troca de senha bem-sucedida", Toast.LENGTH_SHORT).show()
                        navController.navigate("loading")
                    }
                    Log.d(MainActivity::class.java.simpleName, "Troca de senha bem-sucedida")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(MainActivity::class.java.simpleName, "Erro durante a troca de senha: $errorBody")
                    Toast.makeText(context, "Erro durante a troca de senha", Toast.LENGTH_SHORT).show()
                }
            }
        } else{
            Toast.makeText(context, "Por favor, reolhe suas caixas de texto", Toast.LENGTH_SHORT).show()
        }
    }


    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column (
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
                Box (
                    modifier = Modifier.fillMaxWidth()
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
                        Spacer(modifier = Modifier.height(125.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                      navController.navigate("validationCode")
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
                            modifier = Modifier.height(420.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                color = Color.White,
                                text = stringResource(R.string.troca_de_senha).uppercase(),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                            Column (
                                modifier = Modifier
                                    .height(300.dp)
                                    .width(320.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                CustomOutlinedTextField(
                                    value = passwordState,
                                    onValueChange = { passwordState = it },
                                    label = "Senha",
                                    showError = !validatePassword,
                                    errorMessage = validatePasswordError,
                                    isPasswordField = true,
                                    isPasswordVisible = isPasswordVisible,
                                    onVisibilityChange = { isPasswordVisible = it },
                                    leadingIconImageVector = Icons.Default.Password,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                    ),
                                    borderColor = Color.Transparent
                                )

                                CustomOutlinedTextField(
                                    value = repeatPasswordState,
                                    onValueChange = { repeatPasswordState = it },
                                    label = "Repita a senha",
                                    showError = !validateConfirmPassword || !validateArePasswordEqual,
                                    errorMessage = if (!validateConfirmPassword) validatePasswordError else validateEqualPasswordError,
                                    isPasswordField = true,
                                    isPasswordVisible = isConfirmPasswordVisible,
                                    onVisibilityChange = { isConfirmPasswordVisible = it },
                                    leadingIconImageVector = Icons.Default.Password,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = { focusManager.clearFocus() }
                                    ),
                                    borderColor = Color.Transparent
                                )
                                Box(
                                    modifier = Modifier
                                        .width(320.dp)
                                        .height(110.dp)
                                        .background(Color.White)
                                        .shadow(1.dp)
                                ) {
                                    Column() {
                                        Text(
                                            text = stringResource(id = R.string.requerimentos_senha),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black,
                                            textAlign = TextAlign.Left,
                                            fontSize = 10.sp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = stringResource(id = R.string.requerimentos_senha2),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black,
                                            textAlign = TextAlign.Left,
                                            fontSize = 10.sp
                                        )
                                        Text(
                                            text = stringResource(id = R.string.requerimentos_senha3),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black,
                                            textAlign = TextAlign.Left,
                                            fontSize = 10.sp
                                        )
                                        Text(
                                            text = stringResource(id = R.string.requerimentos_senha4),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black,
                                            textAlign = TextAlign.Left,
                                            fontSize = 10.sp
                                        )
                                        Text(
                                            text = stringResource(id = R.string.requerimentos_senha5),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black,
                                            textAlign = TextAlign.Left,
                                            fontSize = 10.sp
                                        )
                                    }

                                }
                            }
                            GradientButton(
                                onClick = {
                                    if (idUser != null) {
                                        updatePassword(idUser, passwordState, repeatPasswordState, viewModel)
                                        Log.e("TAG", "bu: $idUser, $passwordState, $repeatPasswordState, $viewModel", )
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
}
