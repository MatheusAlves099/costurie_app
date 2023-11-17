package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton

@Composable
fun GradientButtonModal (
    onClick: () -> Unit,
    text: String,
    color1: Color,
    color2: Color
) {
    Button(
        onClick,
        modifier = Modifier
            .height(45.dp)
            .width(100.dp)
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        color1,
                        color2
                    )
                ),
                shape = ShapeButton.large
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp
        )
    ) {
        Text(text = text, fontSize = 18.sp, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun GradientButtonModalPreview() {
    GradientButtonModal(onClick = {  }, text = "", color1 = Destaque1, color2 = Destaque2)
}