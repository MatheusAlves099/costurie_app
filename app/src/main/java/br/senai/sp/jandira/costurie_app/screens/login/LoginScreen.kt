package br.senai.sp.jandira.costurie_app.screens.login

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.ExperimentalMaterial3Api
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
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField
import br.senai.sp.jandira.costurie_app.components.GoogleButton
import br.senai.sp.jandira.costurie_app.components.GradientButton
import br.senai.sp.jandira.costurie_app.components.Line
import br.senai.sp.jandira.costurie_app.components.WhiteButton
import br.senai.sp.jandira.costurie_app.function.deleteUserSQLite
import br.senai.sp.jandira.costurie_app.function.saveLogin
import br.senai.sp.jandira.costurie_app.repository.LoginRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, lifecycleScope: LifecycleCoroutineScope, localStorage: Storage) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var emailState by remember {
        mutableStateOf("")
    }

    var passwordState by remember {
        mutableStateOf("")
    }

    var passwordVisibilityState by remember {
        mutableStateOf(false)
    }

    var validateEmail by rememberSaveable {
        mutableStateOf(true)
    }

    var validatePassword by rememberSaveable {
        mutableStateOf(true)
    }

    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isConfirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val validateEmailError = "O formato do e-mail não é válido"
    val validatePasswordError = "Deve misturar letras maiúsculas e minúsculas, pelo menos um número, caracter especial e mínimo de 8 caracteres"

    Log.i("findusers", "${UserRepositorySqlite(context).findUsers()}")

    fun validateData(email: String, password: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$".toRegex()

        validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        validatePassword = passwordRegex.matches(password)

        return validateEmail && validatePassword
    }

    fun login(
        email: String,
        password: String
    ) {
        if (validateData(email, password)) {
            val loginRepository = LoginRepository()
            lifecycleScope.launch {
                val response = loginRepository.loginUser(email, password)

                if (response.isSuccessful) {
                    Log.e(MainActivity::class.java.simpleName, "Login bem-sucedido")
                    Log.e("login", "login: ${response.body()}")
                    val body = response.body()

                    if (body != null) {
                        val checagem = body.get("status")?.asInt
                        if (checagem == 404) {
                            Toast.makeText(context, "Email ou senha inválido", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Seja bem-vindo", Toast.LENGTH_SHORT).show()
                            val token = body.get("token")?.asString
                            val loginObject = body.getAsJsonObject("login")
                            val id = loginObject?.get("id")?.asLong
                            val nome = loginObject?.get("nome_de_usuario")?.asString
                            val email = loginObject?.get("email")?.asString

                            if (id != null && nome != null && token != null && email != null) {
                                // Se os dados do usuário estiverem disponíveis, você pode usá-los aqui.
                                // Faça o que for necessário com esses dados.
                                Log.e("LOGIN - SUCESS - 201", "login: ${response.body()}")
                                Toast.makeText(context, "Bem Vindo $nome", Toast.LENGTH_SHORT).show()

                                if (UserRepositorySqlite(context).findUsers().isEmpty()) {
                                    saveLogin(
                                        context = context,
                                        id = id,
                                        nome = nome,
                                        token = token,
                                        email = email,
                                        senha = password,  // Você pode definir a senha aqui
                                    )
                                } else {
                                    deleteUserSQLite(context = context, id.toInt())
                                    saveLogin(
                                        context = context,
                                        id = id,
                                        nome = nome,
                                        token = token,
                                        email = email,
                                        senha = password,
                                    )
                                }
                                localStorage.salvarValor(context, "0", "currentScreen")
                                navController.navigate("home")

                            } else {
                                Toast.makeText(context, "Dados do usuário inválidos", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()

                    Log.e(MainActivity::class.java.simpleName, "Erro durante o login: $errorBody")
                    Toast.makeText(context, "Email ou senha inválido", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Por favor, reveja suas caixas de texto", Toast.LENGTH_SHORT).show()
        }
    }



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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.forma_topo_login),
                        contentDescription = "",
                        alignment = Alignment.TopEnd,
                        modifier = Modifier.fillMaxSize())

                    Image(
                        painter = painterResource(id = R.drawable.forma_baixo_login),
                        contentDescription = "",
                        alignment = Alignment.BottomEnd,
                        modifier = Modifier.fillMaxSize())

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.modal_login),
                            contentDescription = "",
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                        )
                        Column(
                            modifier = Modifier
                                .width(320.dp)
                                .height(480.dp)
                                .verticalScroll(scrollState),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween

                        ) {
                            Spacer(modifier = Modifier.height(15.dp))

                            Text(
                                text = stringResource(id = R.string.titulo_app),
                                color = Color.White
                            )
                            //Spacer(modifier = Modifier.height(5.dp))
                            CustomOutlinedTextField(
                                value = emailState,
                                onValueChange = { emailState = it },
                                label = "E-mail",
                                showError = !validateEmail,
                                errorMessage = validateEmailError,
                                leadingIconImageVector = Icons.Default.AlternateEmail,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                borderColor = Color.Transparent
                            )

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
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.clearFocus() }
                                ),
                                borderColor = Color.Transparent
                            )

                            //Spacer(modifier = Modifier.height(20.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End,

                                ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate("password")
                                        },
                                    text = "Esqueceu a senha?",
                                    color = Color(168,155,255),
                                    textAlign = TextAlign.Right,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold

                                )
                            }

                            GradientButton(
                                onClick = { login(emailState, passwordState) },
                                text = stringResource(id = R.string.texto_botao_login).uppercase() ,
                                color1 = Destaque1,
                                color2 = Destaque2
                            )

                            Line()

                            GoogleButton( onClick = {}, text = "Entre com o Google")

                            Spacer(modifier = Modifier.height(5.dp))


                            WhiteButton(onClick = {
                                navController.navigate("register")
                            },
                                text = "REGISTRAR-SE")
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen()
//}