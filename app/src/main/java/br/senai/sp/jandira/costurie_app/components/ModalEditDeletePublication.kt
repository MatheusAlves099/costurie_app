package br.senai.sp.jandira.costurie_app.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.repository.PublicationRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import kotlinx.coroutines.launch

@Composable

fun ModalEditDeletePublication(
    lifecycleScope: LifecycleCoroutineScope,
    localStorage: Storage,
    navController: NavController
) {

    var isDialogOpen by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var id = localStorage.lerValor(context, "id_publicacao")

    suspend fun deletePublicationById() {
        val publicationRepository = PublicationRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = publicationRepository.deletePublication(user.token, id!!.toInt())

        if (response.isSuccessful) {
            Log.e(MainActivity::class.java.simpleName, "Publicação Deletada com Sucesso!")
            Log.e("publicação", "publicação: ${response.body()} ")
            Toast.makeText(
                context,
                response.body()?.get("message").toString(),
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate("explore")

        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("DELETAR PUBLICAÇÃO", "Deletar uma publicação: $errorBody")
        }
    }

    Image(
        painter = painterResource(id = R.drawable.baseline_more_horiz_24),
        contentDescription = "",
        modifier = Modifier
            .size(45.dp)
            .clickable {
                isDialogOpen = true
            }
    )


    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpen = false
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .offset(x = 30.dp),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    IconButton(
//                        onClick = { isDialogOpen = false },
//                    ) {
//                        Image(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_close_24),
//                            contentDescription = "",
//                            modifier = Modifier.size(45.dp),
//                            alignment = Alignment.TopEnd
//                        )
//                    }
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
                        text = "Editar Publicação",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Deletar Publicação",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            lifecycleScope.launch {
                                deletePublicationById()
                            }
                        }
                    )
                }
            },
            containerColor = Color.White,
            confirmButton = {}
        )
    }
}