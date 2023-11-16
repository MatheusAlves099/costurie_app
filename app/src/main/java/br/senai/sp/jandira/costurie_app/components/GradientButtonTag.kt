package br.senai.sp.jandira.costurie_app.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel

@Composable
fun GradientButtonTag(
    onClick: () -> Unit,
    color1: Color,
    color2: Color,
    tagId: Int,
    text: String,
    textColor: Color
) {
    val viewModel: TagColorViewModel = viewModel()
    val tagColor = viewModel.getTagColor(tagId)
    val tagTextColor = viewModel.getTagTextColor(tagId)

    //val brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))
    Button(
        onClick,
        modifier = Modifier
            .background(
                Brush.horizontalGradient(
                    colors = tagColor
                ),
                shape = RoundedCornerShape(15.dp),
            )
            .height(IntrinsicSize.Min),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp
        ),
        border = BorderStroke(
            2.dp, Brush.horizontalGradient(
                colors = listOf(
                    Destaque1,
                    Destaque2
                )
            )
        ),
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues.Absolute(
            top = 5.dp,
            right = 11.dp,
            bottom = 5.dp,
            left = 11.dp
        )
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    val brush = Brush.horizontalGradient(tagTextColor)
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                    }
                },
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

class TagColorViewModel : ViewModel() {
    private val tagColors = mutableStateMapOf<Int, List<Color>>()
    private var tagTextColor = mutableStateMapOf<Int, List<Color>>()

    fun getTagColor(tagId: Int): List<Color> {
        return tagColors[tagId] ?: listOf(Color.Transparent, Color.Transparent)
    }

    fun getTagTextColor(tagTextColorId: Int): List<Color> {
        return tagTextColor[tagTextColorId] ?: listOf(Destaque1, Destaque2)
    }

    fun setTagColor(tagId: Int, color1: Color, color2: Color) {
        tagColors[tagId] = listOf(color1, color2)
    }

    fun setTagTextColor(tagTextColorId: Int, color1: Color, color2: Color) {
        tagTextColor[tagTextColorId] = listOf(color1, color2)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GradientButtonTagPreview() {
//    GradientButtonTag(onClick = { }, text = "teste", color1 = Destaque1, color2 = Destaque2, textColor = Color.Black)
//}