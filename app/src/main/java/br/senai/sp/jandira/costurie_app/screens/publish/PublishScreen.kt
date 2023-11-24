package br.senai.sp.jandira.costurie_app.screens.publish

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.GradientButtonTag
import br.senai.sp.jandira.costurie_app.components.ModalSucess
import br.senai.sp.jandira.costurie_app.components.TagColorViewModel
import br.senai.sp.jandira.costurie_app.model.AnexoResponse
import br.senai.sp.jandira.costurie_app.model.TagEditResponse
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.repository.PublicationRepository
import br.senai.sp.jandira.costurie_app.repository.TagsRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Kufam
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PublishScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    localStorage: Storage
) {

    val context = LocalContext.current

    var isDialogOpen by remember { mutableStateOf(false) }

    var anexoResponse by remember {
        mutableStateOf<AnexoResponse>(AnexoResponse(""))
    }

    var titleState by remember {
        mutableStateOf("")
    }

    var descriptionState by remember {
        mutableStateOf("")
    }

    var tagsList by remember() {
        mutableStateOf(listOf<TagEditResponse>())
    }

    //REFERENCIA PARA ACESSO E MANiPULACAO DO CLOUD STORAGE
    val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("teste")

    //REFERENCIA PARA ACESSO E MANIPULACAO DO CLOUD FIRESTORE
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(fotoUri).build()
    )

    var isImageSelected by remember { mutableStateOf(false) }
    var anexo by remember {
        mutableStateOf(AnexoResponse(""))
    }

    var selectedMediaUri by remember { mutableStateOf(emptyList<AnexoResponse>()) }
    var selectedMediaUrl by remember { mutableStateOf(arrayListOf<AnexoResponse>()) }

    val array = UserRepositorySqlite(context).findUsers()

    val user = array[0]

    val tagsArray = mutableListOf<TagResponseId>()

    val tagsRepository = TagsRepository()

    val pesquisaState by remember {
        mutableStateOf("")
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    val viewModel: TagColorViewModel = viewModel()

    fun filtro(text: String): List<TagEditResponse> {
        lifecycleScope.launch {
            tagsList = tagsRepository.getAllTags2(user.token).body()!!.data
        }
        val newList: List<TagEditResponse> = tagsList.filter {
            it.nome_tag.contains(text, ignoreCase = true)
        }
        return newList
    }

    fun createPublication(
        id_usuario: Int,
        token: String,
        titulo: String,
        descricao: String,
        tags: MutableList<TagResponseId>,
        anexos: List<AnexoResponse>
    ) {

        val publicationRepository = PublicationRepository()
        lifecycleScope.launch {
            val array = UserRepositorySqlite(context).findUsers()

            val user = array[0]

            val response = publicationRepository.createPublication(
                user.id.toInt(),
                user.token,
                titulo,
                descricao,
                tags,
                anexos
            )

            Log.e("PUBLICATION0", "user: $response")
            Log.i("PUBLICATION1", "user: ${response.body()}")

            if (response.isSuccessful) {

                Log.e(MainActivity::class.java.simpleName, "Publicação Feita com Sucesso!")
                Log.e("publication", "publication: ${response.body()} ")

                navController.navigate("home")

            } else {
                val errorBody = response.errorBody()?.string()

                val checagem = response.body()
                if (checagem?.titulo == "" || checagem?.descricao == null || checagem?.titulo == null || checagem.descricao == "" || checagem.anexos == null || checagem.tags == null) {
                    Toast.makeText(
                        context,
                        "Campos obrigatórios não foram preenchidos.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Log.e(
                        MainActivity::class.java.simpleName,
                        "Erro durante inserir uma publicação: $errorBody"
                    )
                    Toast.makeText(
                        context,
                        "Erro durante inserir uma publicação",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    fun urlDownload(it: String) {
        val storageRefs: StorageReference =
            FirebaseStorage.getInstance().reference.child("teste/${Uri.parse(it)}")

        val uploadTask = storageRefs.putFile(Uri.parse(it))

        uploadTask
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Log.i(
                        "urlDown",
                        "it: ${it}"
                    )
                    storageRefs.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()

                        firebaseFirestore
                            .collection("images")
                            .add(map)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {

                                    val anexo = AnexoResponse(uri.toString())
                                    selectedMediaUrl.add(anexo)

                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "ERRO AO TENTAR REALIZAR O UPLOAD",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
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
            }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val anexoResponse = AnexoResponse(conteudo = uri.toString())
            selectedMediaUri += anexoResponse
        }
    }

    Costurie_appTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bar_icon),
                    contentDescription = "",
                    Modifier.size(75.dp)
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = "",
                    Modifier
                        .size(35.dp)
                        .clickable {

                        }
                )

//                ModalSucess(
//                    navController,
//                    lifecycleScope,
//                    localStorage
//                )
                Image(
                    painter = painterResource(id = R.drawable.send_icon),
                    contentDescription = "",
                    Modifier
                        .size(35.dp)
                        .clickable {

                            selectedMediaUri.forEach {
                                lifecycleScope.launch {
                                    urlDownload(it.conteudo)
                                }
                            }
                            if (selectedMediaUrl.size == selectedMediaUri.size) {
                                createPublication(
                                    id_usuario = user.id.toInt(),
                                    token = user.token,
                                    titulo = titleState,
                                    descricao = descriptionState,
                                    anexos = selectedMediaUrl,
                                    tags = tagsArray
                                )
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .width(110.dp)
                        .background((Color.White), shape = RoundedCornerShape(20.dp))
                        .border(
                            width = 2.dp,
                            color = Color(168, 155, 255, 255),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.icon_add_image),
                        contentDescription = "",
                        Modifier
                            .size(50.dp)
                            .clickable {
                                launcher.launch("image/*")

                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .background((Color.White), shape = RoundedCornerShape(60.dp))
                    .border(
                        width = 2.dp,
                        color = Color(231, 188, 255, 255),
                        shape = RoundedCornerShape(60.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                LazyRow(content = {
                    items(selectedMediaUri) { anexoResponse ->
                        val uri = anexoResponse.conteudo
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(Color(168, 155, 255, 102))
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = uri,
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.trash_icon),
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        selectedMediaUri = selectedMediaUri.minus(anexoResponse)
                                    },
                                tint = Color.White
                            )
                        }
                    }
                })
            }

            Spacer(modifier = Modifier.height(15.dp))

            CustomOutlinedTextField2(
                value = titleState,
                onValueChange = {
                    titleState = it
                },
                label = stringResource(id = R.string.titulo_label),
                borderColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .padding(horizontal = 35.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomOutlinedTextField2(
                value = descriptionState,
                onValueChange = {
                    descriptionState = it
                },
                label = stringResource(id = R.string.descricao_label),
                borderColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 35.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.tags_title_label),
                modifier = Modifier
                    .padding(horizontal = 35.dp),
                fontFamily = Kufam,
                fontSize = 18.sp,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .background((Color.White), shape = RoundedCornerShape(20.dp))
                    .border(
                        width = 2.dp,
                        color = Color(168, 155, 255, 255),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = rememberLazyGridState(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(12.dp)
                ) {
                    items(filtro(pesquisaState)) {
                        GradientButtonTag(
                            onClick = {
                                isClicked = !isClicked
                                if (isClicked) {
                                    viewModel.setTagColor(it.id, Destaque1, Destaque2)
                                    viewModel.setTagTextColor(
                                        it.id,
                                        Color.White,
                                        Color.White
                                    )
                                    if (!tagsArray.contains(TagResponseId(it.id))) {
                                        tagsArray.add(TagResponseId(it.id))
                                    }
                                } else {
                                    viewModel.setTagColor(
                                        it.id,
                                        Color.Transparent,
                                        Color.Transparent
                                    )
                                    viewModel.setTagTextColor(
                                        it.id,
                                        Destaque1,
                                        Destaque2
                                    )
                                    if (tagsArray.contains(TagResponseId(it.id))) {
                                        tagsArray.remove(TagResponseId(it.id))
                                    }
                                }

                                Log.e("it.nome", "nometag: ${it.nome_tag}")
                                Log.e("it.id", "id: ${it.id}")
                                Log.e("array", "array: $tagsArray")
                            },
                            tagId = it.id,
                            color1 = Destaque1,
                            color2 = Destaque2,
                            text = it.nome_tag,
                            textColor = Color(168, 155, 255, 255),

                            )
                    }
                }
            }
        }

    }
}






