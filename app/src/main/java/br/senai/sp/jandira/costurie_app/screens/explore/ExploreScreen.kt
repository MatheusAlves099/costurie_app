package br.senai.sp.jandira.costurie_app.screens.explore

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme

@Composable
fun ExploreScreen(navController: NavController) {
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
                                        .background(Color(168, 155, 255, 102), shape = RoundedCornerShape(16.dp))
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
                    items(4) { publication ->
                        Card(
                            modifier = Modifier
                                .width(170.dp)
                                .height(220.dp)
                                .padding(start = 16.dp, 2.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {

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
                                    Image(
                                        painter = painterResource(id = R.drawable.mulher_publicacao2),
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
            }
        }
    }
}