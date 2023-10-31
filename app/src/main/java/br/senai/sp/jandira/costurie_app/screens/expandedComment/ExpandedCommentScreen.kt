package br.senai.sp.jandira.costurie_app.screens.expandedComment

import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextFieldComment
import br.senai.sp.jandira.costurie_app.components.GradientButton
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandedCommentScreen(
) {
    Costurie_appTheme {

        var commentState by remember {
            mutableStateOf("")
        }

        val sheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        )
        val scope = rememberCoroutineScope()
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShape = RoundedCornerShape(20.dp),
            sheetElevation = 10.dp,
            sheetContent = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                ) {
                    Row (
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

                    Spacer(modifier = Modifier.height(15.dp))

                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, top = 60.dp, end = 30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = "",
                            Modifier
                                .size(35.dp)
                                .clickable { }
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .height(630.dp)
                ) {
                    items(7) {
                        Card(
                            modifier = Modifier
                                .size(380.dp, 85.dp)
                                .padding(start = 25.dp, top = 4.dp, bottom = 4.dp)
                                .clickable {
                                },
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(15.dp),
                            elevation = AppBarDefaults.TopAppBarElevation
                        ) {
                            Row(
                                modifier = Modifier
                                    .width(300.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(shape = RoundedCornerShape(10.dp))
                                        .background(Color(168, 155, 255, 102))
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.mulher_publicacao),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(bottom = 5.dp, end = 2.dp)
                                            .clip(shape = RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Column {
                                    Text(
                                        text = "Cyclanilda Soares",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .width(250.dp)
                                            .height(18.dp),
                                        fontSize = 12.sp,
                                        color = Contraste,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Text(
                                        text = "Parabéns pelo trabalho!!",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .width(250.dp)
                                            .height(18.dp),
                                        fontSize = 12.sp,
                                        color = Contraste
                                    )

                                    Text(
                                        text = "Responder",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .width(250.dp)
                                            .height(25.dp),
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                CustomOutlinedTextFieldComment(
                    value = commentState,
                    onValueChange = {
                        commentState = it
                    },
                    label = stringResource(id = R.string.label_comentarios)
                )
            },
            sheetBackgroundColor = Color.White,
            sheetPeekHeight = 0.dp
        ) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    scope.launch {
                        if(sheetState.isCollapsed) {
                            sheetState.expand()
                        } else {
                            sheetState.collapse()
                        }
                    }
                }) {
                    Text(text = "Comentários", color = Color.Black)
                }
            }
        }
    }
}