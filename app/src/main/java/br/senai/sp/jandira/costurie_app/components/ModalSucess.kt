package br.senai.sp.jandira.costurie_app.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.model.AnexoResponse
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.repository.PublicationRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import kotlinx.coroutines.launch

@Composable
fun ModalSucess(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    localStorage: Storage
) {
    val context = LocalContext.current

    var isDialogOpen by remember { mutableStateOf(false) }

    val array = UserRepositorySqlite(context).findUsers()

    val user = array[0]

    var selectedMediaUri by remember { mutableStateOf(emptyList<AnexoResponse>()) }
    var selectedMediaUrl by remember { mutableStateOf(arrayListOf<AnexoResponse>()) }

    var titleState by remember {
        mutableStateOf("")
    }

    var descriptionState by remember {
        mutableStateOf("")
    }

    val tagsArray = mutableListOf<TagResponseId>()

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

    Image(
        painter = painterResource(id = R.drawable.send_icon),
        contentDescription = "",
        Modifier
            .size(35.dp)
            .clickable {
                isDialogOpen = true

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

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpen = false
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TESTE",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row (
                        modifier = Modifier
                            .width(220.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        GradientButtonModal(
                            onClick = {

                            },
                            text = stringResource(id = R.string.text_yes).uppercase(),
                            color1 = Destaque1,
                            color2 = Destaque2
                        )
                    }
                }
            },
            containerColor = Color.White,
            confirmButton = {}
        )
    }
}