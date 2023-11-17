package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton

@Composable
fun ButtonSettings(
    onClick: () -> Unit,
    text: String,
    icon: Painter
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .width(330.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(
            2.dp, Color(168, 155, 255, 255)
        ),
        colors = ButtonDefaults.buttonColors(
            //containerColor = Color(168, 155, 255, 255)
            containerColor = Color.White
        )

    ) {
        Row(

            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = text,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(168, 155, 255, 255),
            )
            //Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = icon,
                contentDescription = "",
                modifier = Modifier
                    .size(12.dp),
                alignment = Alignment.BottomEnd
            )

        }
    }
}