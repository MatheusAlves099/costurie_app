package br.senai.sp.jandira.costurie_app.screens.personalization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton
import kotlin.math.cos

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun typeProfileScreenPreview() {
//    TypeProfileScreen()
//}

@Composable
fun TypeProfileScreen(navController: NavController, lifecycleScope: LifecycleCoroutineScope, localStorage: Storage) {
    //navController: NavController, lifecycleScope: LifecycleCoroutineScope
    val context = LocalContext.current
    val brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))
    var nomeState by remember {
        mutableStateOf("")
    }
    var costureiraSelected: Boolean by remember {
        mutableStateOf(false)
    }
    var consumidorSelected: Boolean by remember {
        mutableStateOf(false)
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.navigate("location")
                        },

                        ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                        )
                    }
                    Button(
                        onClick = {
                                  if(costureiraSelected && consumidorSelected == false) {
                                      navController.navigate("tagSelection")
                                  } else if (consumidorSelected && costureiraSelected == false) {
                                      localStorage.salvarValor(context, "0", "currentScreen")
                                      navController.navigate("home")
                                  }
                        },
                        modifier = Modifier
                            .size(45.dp)
                            .background(
                                brush = brush,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }
                }//row
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(

                        text = stringResource(id = R.string.texto_tipo_de_perfil).uppercase(),
                        modifier = Modifier.height(30.dp),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                }
                Column(
                    modifier = Modifier.height(288.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        {
                            costureiraSelected = true
                            consumidorSelected = false
                        },
                        modifier = Modifier
                            .height(100.dp)
                            .width(288.dp)
                            .fillMaxWidth(),
                        shape = ShapeButton.large,
                        border = BorderStroke(
                            2.dp, Color(168, 155, 255, 255)
                        ),
                        colors = if (costureiraSelected) {
                            ButtonDefaults.buttonColors(
                                containerColor = Color(168, 155, 255, 255)
                            )
                        } else {
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = if (costureiraSelected) {
                                    painterResource(id = R.drawable.bola_de_la_branca)
                                } else {
                                    painterResource(id = R.drawable.bola_de_la2)
                                },
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp, 36.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(id = R.string.perfil_costureira),
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = if (costureiraSelected) {
                                    Color.White
                                } else {
                                    Color(168, 155, 255, 255)
                                }

                            )
                        }


                    }


                    Button(
                        {

                            consumidorSelected = true
                            costureiraSelected = false

                        },
                        modifier = Modifier
                            .height(100.dp)
                            .width(288.dp)
                            .fillMaxWidth(),
                        shape = ShapeButton.large,
                        border = BorderStroke(
                            2.dp, Color(168, 155, 255, 255)
                        ),
                        colors = if (consumidorSelected) {
                            ButtonDefaults.buttonColors(
                                containerColor = Color(168, 155, 255, 255)
                            )
                        } else {
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        },
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = if (consumidorSelected) {
                                    painterResource(id = R.drawable.person_white)
                                } else {
                                    painterResource(id = R.drawable.person)
                                },
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp, 36.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(id = R.string.perfil_consumidor),
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = if (consumidorSelected) {
                                    Color.White
                                } else {
                                    Color(168, 155, 255, 255)
                                }
                            )
                        }


                    }
                }
            }


        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewTypeProfileScreen() {
//    TypeProfileScreen()
//}
