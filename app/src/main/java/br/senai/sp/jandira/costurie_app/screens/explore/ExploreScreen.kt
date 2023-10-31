package br.senai.sp.jandira.costurie_app.screens.explore

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.model.PublicationGetResponse
import br.senai.sp.jandira.costurie_app.repository.PublicationRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import coil.compose.AsyncImage

@Composable
fun ExploreScreen(navController: NavController,  localStorage: Storage) {

    var context = LocalContext.current

    val publicationsState = remember { mutableStateOf(emptyList<PublicationGetResponse>()) }

    suspend fun getAllPublications() {
        val publicationRepository = PublicationRepository()
        val array = UserRepositorySqlite(context).findUsers()
        val user = array[0]

        val response = publicationRepository.getAllPublications()

        if (response.isSuccessful) {
            val publications = response.body()?.publicacoes ?: emptyList()
            publicationsState.value = publications
            Log.i("publis", "getAllPublications: ${publications}")
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("TODAS AS PUBLICACOES", "Erro: $errorBody")
            Toast.makeText(
                context,
                "Erro ao buscar todas as publicações: $errorBody",
                Toast.LENGTH_SHORT
            ).show()
        }
    }




    LaunchedEffect(key1 = true) {
        val array = UserRepositorySqlite(context).findUsers()

        val user = array[0]

        getAllPublications()

        Log.e("PUBLICATION", "ExploreScreen: ${getAllPublications()}")
    }

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 10.dp),
                    color = Color.Black,
                    text = stringResource(id = R.string.explorar_titulo),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                    color = Color.Black,
                    text = stringResource(id = R.string.mais_populares_text),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp,
                )

                LazyRow(
                ) {
                    items(4) { publication ->

                        Card(
                            modifier = Modifier
                                .width(170.dp)
                                .height(220.dp)
                                .padding(start = 16.dp, 2.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                },
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(145.dp)
                                        .fillMaxWidth()
                                        .background(
                                            Color(168, 155, 255, 102),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.mulher_publicacao),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(150.dp, 140.dp)
                                            .clip(shape = RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        text = "Beltrana dos Santos Silva",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Contraste
                                    )
                                    Text(
                                        text = "Descrição",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 10.dp),
                    color = Color.Black,
                    text = stringResource(id = R.string.recentes_text),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow() {
                    items(publicationsState.value.reversed()) { publication ->
                        var shortDesc = publication.descricao
                        var titleList = publication.titulo.split(" ")
                        var shortTitle = ""

                        if (shortDesc.length > 30) {
                            shortDesc = shortDesc.substring(0, 30).plus("...")
                        }
                        titleList.forEach { string ->
                            if (titleList.indexOf(string) < 4) {
                                shortTitle += "$string "
                            } else if (titleList.indexOf(string) == 4) {
                                shortTitle += "..."
                            }
                        }

                        Card(
                            modifier = Modifier
                                .width(170.dp)
                                .height(220.dp)
                                .padding(start = 16.dp, 2.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    localStorage.salvarValor(
                                        context,
                                        publication.id.toString(),
                                        "id_publicacao"
                                    )
                                    navController.navigate("expandedPublication")
                                },
                            backgroundColor = Color.White,
                            elevation = 20.dp
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(145.dp)
                                        .fillMaxWidth()
                                        .background(
                                            Color(168, 155, 255, 102),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                ) {
                                    AsyncImage(
                                        model = publication.anexos[0].anexo,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(150.dp, 140.dp)
                                            .clip(shape = RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        text = shortTitle,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Contraste
                                    )
                                    Text(
                                        text = shortDesc,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}