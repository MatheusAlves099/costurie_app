package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton


@Composable
fun ButtonGivePoint(
    onClick: () -> Unit,
    text: String,
    btnColor: List<Color>,
    textColor: List<Color>

) {

//    val viewModel: ButtonPointColorViewModel = viewModel()
//    if (btnColor == null) { viewModel.getPointButtonColor(pointId) }
//    if (textColor == null) { viewModel.getPointTextColor(pointId) }
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
                .width(130.dp)
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
                    .clickable { onClick() }
                    .background(
                        Brush.horizontalGradient(
                            colors = btnColor
                        ),
                        shape = RoundedCornerShape(15.dp),
                    ),
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
                    textAlign = TextAlign.Center,
                    modifier = Modifier.height(18.dp)
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            val brush = Brush.horizontalGradient(textColor)
                            onDrawWithContent {
                                drawContent()
                                drawRect(brush, blendMode = BlendMode.SrcAtop)
                            }
                        }
                )
            }

        }
    }


}

//class ButtonPointColorViewModel : ViewModel() {
//    private val ButtonColor = mutableStateMapOf<Int, List<Color>>()
//    private var ButtonTextColor = mutableStateMapOf<Int, List<Color>>()
//
//    fun getPointButtonColor(pointId: Int): List<Color> {
//        return ButtonColor[pointId] ?: listOf(Color.Transparent, Color.Transparent)
//    }
//
//    fun getPointTextColor(pointTextColorId: Int): List<Color> {
//        return ButtonTextColor[pointTextColorId] ?: listOf(Destaque1, Destaque2)
//    }
//
//    fun setPointButtonColor(pointId: Int, color1: Color, color2: Color) {
//        ButtonColor[pointId] = listOf(color1, color2)
//    }
//
//    fun setPointTextColor(pointTextColorId: Int, color1: Color, color2: Color) {
//        ButtonTextColor[pointTextColorId] = listOf(color1, color2)
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun ButtonGivePointPreview() {
//    ButtonGivePoint(onClick = { }, text = "Dar Ponto", 1)
//}