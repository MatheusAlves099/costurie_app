package br.senai.sp.jandira.costurie_app.screens.chats

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.components.MessageBar
import br.senai.sp.jandira.costurie_app.components.ReceivedMesssage
import br.senai.sp.jandira.costurie_app.components.SendMesssage
import br.senai.sp.jandira.costurie_app.model.MessageResponse
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Principal1
import br.senai.sp.jandira.costurie_app.ui.theme.Principal2

@SuppressLint("SuspiciousIndentation")
@Composable
fun ChatScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
) {
    var messageState by remember {
        mutableStateOf("")
    }

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Principal2
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )

                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(Color(255, 255, 255, 255))
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
                                text = "Beltrana da Silva Santos",
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .width(250.dp)
                                    .height(20.dp),
                                fontSize = 15.sp,
                                color = Principal1
                            )

                            Text(
                                text = "Online",
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .width(250.dp),
                                fontSize = 12.sp,
                                color = Color(252, 246, 255, 179)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.height(644.dp)
                ) {
                    SendMesssage(
                        message = "Teste",
                        time = "15:20"
                    )

                    ReceivedMesssage(
                        message = "Opa",
                        time = "15:21"
                    )
                }

                MessageBar(
                    value = messageState,
                    onValueChange = { },
                    isReplyMode = true
                )
            }
        }
    }
}