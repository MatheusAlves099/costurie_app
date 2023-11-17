package br.senai.sp.jandira.costurie_app.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1

@Composable
fun TermsAndConditionsScreen(navController: NavController) {

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.retangulo_topo),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        alignment = Alignment.TopEnd
                    )
                    Row(
                        modifier = Modifier
                            .width(370.dp)
                            .padding(top = 15.dp, start = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    navController.navigate("settings")
                                }
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.text_terms_and_conditions).uppercase(),
                        modifier = Modifier.height(30.dp),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(46.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(370.dp)
                            .shadow(elevation = 1.dp, shape = RoundedCornerShape(1.dp), spotColor = Destaque1)
                    ) {
                        item {
                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_first),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )

                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_second),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )

                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_third),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )

                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_fourth),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )

                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_fifth),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )

                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_sixth),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )

                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_seventh),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )

                            Text(
                                text = stringResource(id = R.string.description_terms_and_conditions_eighth),
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}