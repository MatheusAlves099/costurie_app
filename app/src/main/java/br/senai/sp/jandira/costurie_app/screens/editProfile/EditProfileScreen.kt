package br.senai.sp.jandira.costurie_app.screens.editProfile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.DropdownBairro
import br.senai.sp.jandira.costurie_app.components.DropdownCidade
import br.senai.sp.jandira.costurie_app.components.DropdownEstado
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.repository.UserRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.viewModel.BairroViewModel
import br.senai.sp.jandira.costurie_app.viewModel.EstadoViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavController,
    viewModel: UserViewModel,
    localStorage: Storage
) {

    val viewModelEstado = viewModel<EstadoViewModel>()

    val viewModelCidade = viewModel<BairroViewModel>()

    val viewModelBairro = viewModel<BairroViewModel>()

    val viewModelIdUsuario = viewModel.id_usuario

    val viewModelIdLocalizacao = viewModel.id_localizacao

    val viewModelNome = viewModel.nome

    val viewModelDescricao = viewModel.descricao

    val viewModelTagUsuario = viewModel.nome_de_usuario

    //REFERENCIA PARA ACESSO E MANiPULACAO DO CLOUD STORAGE
    var storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("images")

    //REFERENCIA PARA ACESSO E MANIPULACAO DO CLOUD FIRESTORE
    var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var nomeState by remember {
        mutableStateOf(viewModelNome)
    }

    var tagDeUsuarioState by remember {
        mutableStateOf(viewModelTagUsuario)
    }

    var descricaoState by remember {
        mutableStateOf(viewModelDescricao)
    }

    var fotoUri by remember {
        mutableStateOf<Uri?>(viewModel.foto)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        fotoUri = it
    }

    var context = LocalContext.current

    var painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(fotoUri).build()
    )

    var cidadeStateUser by remember { mutableStateOf(localStorage.lerValor(context, "cidade")) }
    var estadoStateUser by remember { mutableStateOf(localStorage.lerValor(context, "estado")) }
    var bairroStateUser by remember { mutableStateOf(localStorage.lerValor(context, "bairro")) }

    val estados = viewModel.estados.value ?: emptyList()
    val cidades = viewModel.cidades.value ?: emptyList()
    val bairros = viewModel.bairros.value ?: emptyList()

    val array = UserRepositorySqlite(context).findUsers()

    val user = array[0]

    val scrollState = rememberScrollState()

    Costurie_appTheme {

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.forma_tela_perfil),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.TopStart
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(45.dp)
                                    .clickable {
                                        navController.navigate("home")
                                    }
                            )
                            Image(
                                painter = painterResource(id = R.drawable.edit_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {

                                        val id_usuario = user.id.toInt()
                                        val token = user.token
                                        val id_localizacao = viewModelIdLocalizacao
                                        val bairro = bairroStateUser
                                        val cidade = cidadeStateUser
                                        val estado = estadoStateUser
                                        val descricao = descricaoState
                                        val foto = fotoUri
                                        Log.w("foto", "EditProfileScreen: $foto", )
                                        val nome_de_usuario = tagDeUsuarioState
                                        val nome = nomeState

                                        foto?.let {
                                            storageRef
                                                .putFile(it)
                                                .addOnCompleteListener { task ->

                                                    if (task.isSuccessful) {

                                                        storageRef.downloadUrl.addOnSuccessListener { uri ->

                                                            val map = HashMap<String, Any>()
                                                            map["pic"] = uri.toString()

                                                            firebaseFirestore
                                                                .collection("images")
                                                                .add(map)
                                                                .addOnCompleteListener { firestoreTask ->

                                                                    if (firestoreTask.isSuccessful) {
                                                                        Toast
                                                                            .makeText(
                                                                                context,
                                                                                "UPLOAD REALIZADO COM SUCESSO",
                                                                                Toast.LENGTH_SHORT
                                                                            )
                                                                            .show()
                                                                    } else {
                                                                        Toast
                                                                            .makeText(
                                                                                context,
                                                                                "ERRO AO TENTAR REALIZAR O UPLOAD",
                                                                                Toast.LENGTH_SHORT
                                                                            )
                                                                            .show()
                                                                    }

                                                                    localStorage.salvarValor(
                                                                        context,
                                                                        uri.toString(),
                                                                        "foto"
                                                                    )
//                                                                    localStorage.salvarValor(
//                                                                        context,
//                                                                        foto.toString(),
//                                                                        "foto"
//                                                                    )
                                                                    //BARRA DE PROGRESSO DO UPLOAD
                                                                }
                                                        }

                                                    } else {

                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "ERRO AO TENTAR REALIZAR O UPLOAD",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()

                                                    }

                                                    //BARRA DE PROGRESSO DO UPLOAD

                                                }
                                        }

                                        localStorage.salvarValor(
                                            context,
                                            id_usuario.toString(),
                                            "id"
                                        )
                                        localStorage.salvarValor(context, token, "token")
                                        localStorage.salvarValor(
                                            context,
                                            id_localizacao.toString(),
                                            "id_localizacao"
                                        )
                                        localStorage.salvarValor(context, bairro!!, "bairro")
                                        localStorage.salvarValor(context, estado!!, "estado")
                                        localStorage.salvarValor(context, cidade!!, "cidade")
                                        localStorage.salvarValor(context, descricao, "descricao")
                                        localStorage.salvarValor(
                                            context,
                                            nome_de_usuario,
                                            "nome_de_usuario"
                                        )
                                        localStorage.salvarValor(context, nome, "nome")


                                        navController.navigate("tagsEditProfile")
                                    },
                                alignment = Alignment.TopEnd
                            )

                            Image(
                                painter = painterResource(id = R.drawable.save_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {
                                        Log.e("user-teste", "EditProfileScreen: $user")

                                        if (viewModelIdLocalizacao != null) {
                                            Log.i("verifica", "${estadoStateUser!!}")
                                            localStorage.salvarValor(
                                                context,
                                                estadoStateUser!!,
                                                "estado"
                                            )
                                            Log.i(
                                                "verifica",
                                                "${localStorage.lerValor(context, "estado")}"
                                            )


                                            localStorage.salvarValor(
                                                context,
                                                bairroStateUser!!,
                                                "bairro"
                                            )
                                            localStorage.salvarValor(
                                                context,
                                                cidadeStateUser!!,
                                                "cidade"
                                            )
                                            localStorage.salvarValor(
                                                context,
                                                descricaoState,
                                                "descricao"
                                            )
                                            localStorage.salvarValor(
                                                context,
                                                tagDeUsuarioState,
                                                "nome_de_usuario"
                                            )
                                            localStorage.salvarValor(context, nomeState, "nome")

                                            lifecycleScope.launch {
                                                var response = UserRepository().updateUser(
                                                    id = user.id.toInt(),
                                                    token = user.token,
                                                    id_localizacao = viewModel.id_localizacao!!,
                                                    bairro = localStorage.lerValor(
                                                        context,
                                                        "bairro"
                                                    )!!,
                                                    cidade = localStorage.lerValor(
                                                        context,
                                                        "cidade"
                                                    )!!,
                                                    estado = localStorage.lerValor(
                                                        context,
                                                        "estado"
                                                    )!!,
                                                    descricao = localStorage.lerValor(
                                                        context,
                                                        "descricao"
                                                    )!!,
                                                    foto = localStorage.lerValor(context, "foto"),
                                                    nome_de_usuario = localStorage.lerValor(
                                                        context,
                                                        "nome_de_usuario"
                                                    )!!,
                                                    nome = localStorage.lerValor(context, "nome")!!,
                                                    tags = emptyList<TagResponseId>()
                                                )

                                                if (response.isSuccessful) {
                                                    Log.e(
                                                        MainActivity::class.java.simpleName,
                                                        "Usuário atualizado com sucesso"
                                                    )
                                                    Log.e("user", "user: ${response.body()} ")

                                                    viewModel.setProfileEditSuccess(true)

                                                    navController.popBackStack()
                                                } else {
                                                    val errorBody = response
                                                        .errorBody()
                                                        ?.string()
                                                    Log.e(
                                                        "EDICAO DE PERFIL",
                                                        "updateUser: $errorBody"
                                                    )
                                                    val descricao = response.body()?.descricao
                                                    if (descricao != null) {
                                                        if (descricao.length > 255)
                                                            Log.e(
                                                                MainActivity::class.java.simpleName,
                                                                "Erro durante a atualização dos dados do usuário: ${errorBody}"
                                                            )
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Descricão grande demais",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    }
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "Erro durante a atualização dos dados do usuário: $errorBody",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }
                                            }
                                        }
                                    }
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            if (fotoUri == null) {
                                AsyncImage(
                                    model = "$fotoUri",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            launcher.launch("image/*")
                                            Log.w("foto", "EditProfileScreen: $fotoUri", )
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            } else {

                                AsyncImage(
                                    model = "$fotoUri",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .border(
                                            BorderStroke(borderWidth, Color.White),
                                            RoundedCornerShape(10.dp)
                                        )
                                        .padding(borderWidth)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            launcher.launch("image/*")
                                            Log.w("foto", "EditProfileScreen: $fotoUri", )
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            }

                        }

                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 8.dp, 20.dp, 0.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(

                            text = "NOME",
                            fontSize = 20.sp,
                            color = Color.Black

                        )
                        CustomOutlinedTextField2(
                            value = nomeState,
                            onValueChange = {
                                nomeState = it
                            },
                            borderColor = Color.Transparent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(62.dp)
                        )
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Tag De Usuário",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        CustomOutlinedTextField2(
                            value = tagDeUsuarioState,
                            onValueChange = {
                                tagDeUsuarioState = it
                            },
                            borderColor = Color.Transparent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(62.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Estado", fontSize = 20.sp, color = Color.Black)
                        DropdownEstado(
                            lifecycleScope = lifecycleScope,
                            viewModelEstado,
                        ) { selectedEstado ->
                            estadoStateUser = selectedEstado
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                            .width(180.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = "Cidade", fontSize = 20.sp, color = Color.Black)
                            DropdownCidade(
                                lifecycleScope = lifecycleScope,
                                viewModelEstado,
                                viewModelCidade
                            ) { selectedCidade ->
                                cidadeStateUser = selectedCidade
                            }

                        }
                        Column(
                            modifier = Modifier
                            .width(180.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = "Bairro", fontSize = 20.sp, color = Color.Black)
                            DropdownBairro(
                                lifecycleScope = lifecycleScope,
                                viewModelCidade
                            ) { selectedBairro ->
                                bairroStateUser = selectedBairro
                            }
                        }
                    }


                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Descrição", fontSize = 20.sp, color = Color.Black)
                        CustomOutlinedTextField2(
                            value = descricaoState,
                            onValueChange = {
                                descricaoState = it
                            },
                            borderColor = Color.Transparent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                    }

                }
            }
        }
    }
}

val borderWidth = 1.dp

