package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Principal1
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton


@Composable
fun SendMesssage(
    message: String,
    time: String,
    onDelete: () -> Unit
) {

    var isLongPressActive by remember { mutableStateOf(false) }

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp, start = 310.dp)
    ) {
        Card(
            modifier = Modifier
                .height(35.dp)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, _, _ ->
                        if (pan != Offset(0f, 0f)) {
                            isLongPressActive = true
                        }
                    }
                },
            backgroundColor = Destaque2,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Text(
                    text = message,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(250.dp)
                        .height(20.dp),
                    fontSize = 14.sp,
                    color = Principal1
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