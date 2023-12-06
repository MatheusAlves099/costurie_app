package br.senai.sp.jandira.costurie_app.screens.register

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.PermIdentity
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField
import br.senai.sp.jandira.costurie_app.components.GoogleButton
import br.senai.sp.jandira.costurie_app.components.GradientButton
import br.senai.sp.jandira.costurie_app.components.Line
import br.senai.sp.jandira.costurie_app.components.WhiteButton
import br.senai.sp.jandira.costurie_app.function.deleteUserSQLite
import br.senai.sp.jandira.costurie_app.function.saveLogin
import br.senai.sp.jandira.costurie_app.repository.CadastroRepository
import br.senai.sp.jandira.costurie_app.service.RetrofitFactory
import br.senai.sp.jandira.costurie_app.service.UserService
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, lifecycleScope: LifecycleCoroutineScope) {

    lateinit var apiService: UserService

    Costurie_appTheme {

        var nameState by remember  {
            mutableStateOf("")
        }

        var emailState by remember {
            mutableStateOf("")
        }

        var passwordState by remember {
            mutableStateOf("")
        }

        apiService = RetrofitFactory.getInstance().create(UserService::class.java)

        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        val scrollState = rememberScrollState()

        var repeatPasswordState by remember {
            mutableStateOf("")
        }

        var validateName by rememberSaveable {
            mutableStateOf(true)
        }

        var validateEmail by rememberSaveable {
            mutableStateOf(true)
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

        val validateNameError = "Nome não pode ficar em branco"
        val validateEmailError = "O formato do e-mail não é válido"
        val validatePasswordError = "Deve misturar letras maiúsculas e minúsculas, pelo menos um número, caracter especial e mínimo de 8 caracteres"
        val validateEqualPasswordError = "As senhas devem ser iguais"

        fun validateData(name: String, email: String, password: String, confirmPassword: String): Boolean {
            val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$".toRegex()

            validateName = name.isNotBlank()
            validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            validatePassword = passwordRegex.matches(password)
            validateConfirmPassword = passwordRegex.matches(confirmPassword)
            validateArePasswordEqual = password == confirmPassword

            return validateName && validateEmail && validatePassword && validateConfirmPassword && validateArePasswordEqual
        }

        fun register (
            name: String,
            email: String,
            password: String,
            confirmPassword: String
        ) {
            if(validateData(name, email, password, confirmPassword)){
                val userRepository = CadastroRepository()
                lifecycleScope.launch {
                    val response = userRepository.registerUser(name, email, password)
                    var resultId = response.body()?.getAsJsonObject("usuario")
                    var resulToken = response.body()?.get("token")!!.asString
                    Log.i("location", "${response.body()}")

                    if (response.isSuccessful) {
                        val checagem = response.body()?.get("status")
                        if (checagem.toString() == "400") {
                            Toast.makeText(context, "Campos obrigatórios não foram preenchidos.", Toast.LENGTH_LONG).show()
                        } else {
                            if (UserRepositorySqlite(context).findUsers().isEmpty()) {
                                resultId?.get("id_usuario")?.asLong?.let { id ->
                                    saveLogin(
                                        context = context,
                                        id = id,
                                        nome = name,
                                        token = resulToken.trim('"'),
                                        email = email,
                                        senha = password  // Você pode definir a senha aqui
                                    )
                                }
                            } else {
                                deleteUserSQLite(context = context, resultId?.get("id_usuario")?.asInt)
                                resultId?.get("id_usuario")?.asLong?.let {id ->
                                    saveLogin(
                                        context = context,
                                        id = id,
                                        nome = name,
                                        token = resulToken.trim('"'),
                                        email = email,
                                        senha = password  // Você pode definir a senha aqui
                                    )
                                }
                            }
                            Toast.makeText(context, "Seja bem-vindo", Toast.LENGTH_SHORT).show()
                            navController.navigate("name")
                        }
                        Log.d(MainActivity::class.java.simpleName, "Registro bem-sucedido")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(MainActivity::class.java.simpleName, "Erro durante o registro: $errorBody")
                        Toast.makeText(context, "Erro durante o registro", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Por favor, reveja suas caixas de texto", Toast.LENGTH_SHORT).show()
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

                Box (
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.forma_registrar_se),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )

                    Column (
                        modifier = Modifier
                            .width(320.dp)
                            .height(570.dp)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.texto_registrar_se).uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            modifier = Modifier.height(30.dp)
                        )

                        CustomOutlinedTextField(
                            value = nameState,
                            onValueChange = { nameState = it },
                            label = "Digite um nome de usuário",
                            showError = !validateName,
                            errorMessage = validateNameError,
                            leadingIconImageVector = Icons.Default.PermIdentity,
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

                        Spacer(modifier = Modifier.height(5.dp))

                        GradientButton(
                            onClick = { register(nameState, emailState, passwordState, repeatPasswordState) },
                            text = stringResource(id = R.string.texto_botao_registrar).uppercase(),
                            color1 = Destaque1,
                            color2 = Destaque2
                        )

                        Line()

                        WhiteButton(onClick = {
                            navController.navigate("login")
                        }, text = stringResource(id = R.string.texto_botao_login).uppercase())
                    }
                }
            }
        }
    }
}