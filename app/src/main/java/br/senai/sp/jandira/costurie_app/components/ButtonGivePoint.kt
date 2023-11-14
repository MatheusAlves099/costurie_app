package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton


@Composable
fun ButtonGivePoint(
    onClick: () -> Unit,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Surface(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.destaque_2),
                    shape = ShapeButton.large
                )
                .width(170.dp)
                .height(30.dp)
                .clickable {

                },
            color = Color.White
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
            ) {


                Image(
                    painter = painterResource(id = R.drawable.icon_agulha),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(24.dp)
                        .width(10.dp)
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.destaque_2),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.height(18.dp)
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun ButtonGivePointPreview() {
    ButtonGivePoint(onClick = { }, text = "")
}