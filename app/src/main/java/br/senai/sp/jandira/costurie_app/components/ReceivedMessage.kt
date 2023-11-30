package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.Principal1
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton


@Composable
fun ReceivedMesssage(
    message: String,
    time: String,
    maxBubbleWidth: Dp = 200.dp
) {

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier,
            backgroundColor = Contraste,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(12.dp)
            ){
                if (message.length > 25) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .width(maxBubbleWidth)
                            .padding(end = 10.dp),
                        color = Principal1
                    )
                } else {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .padding(end = 10.dp),
                        color = Principal1
                    )
                }
                Text(
                    text = time,
                    fontSize = 8.sp,
                    color = Principal1
                )
            }
        }
    }
}