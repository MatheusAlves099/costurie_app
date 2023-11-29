package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Principal1
import coil.compose.AsyncImage

@Composable
fun ReceivedMessagePicture(
    mensagem: String,
    time: String,
    foto: String
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        androidx.compose.material.Card(
            shape = RoundedCornerShape(
                topStart = 16.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp
            ),
            modifier = Modifier.width(280.dp),
            backgroundColor = Destaque2
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                AsyncImage(
                    model = foto,
                    contentDescription = ""
                )
                Text(
                    text = time,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .width(250.dp),
                    fontSize = 8.sp,
                    color = Principal1
                )

            }
        }
    }
}