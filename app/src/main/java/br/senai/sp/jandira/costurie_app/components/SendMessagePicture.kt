package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Principal1
import coil.compose.AsyncImage

@Composable
fun SendMessagePicture(
    mensagem: String,
    time: String,
    foto: String,
    onDelete: () -> Unit
) {

    var isLongPressActive by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        androidx.compose.material.Card(
            shape = RoundedCornerShape(
                topStart = 0.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp
            ),
            modifier = Modifier
                .width(280.dp)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, _, _ ->
                        if (pan != Offset(0f, 0f)) {
                            isLongPressActive = true
                        }
                    }
                },
            backgroundColor = Contraste
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

            if (isLongPressActive) {
                Icon(
                    painter = painterResource(id = R.drawable.trash_icon),
                    contentDescription = "Delete",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(8.dp)
                        .clickable {
                            onDelete()
                            isLongPressActive = false
                        },
                    tint = Color.White
                )
            }
        }
    }
}