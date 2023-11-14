package br.senai.sp.jandira.costurie_app.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MessageBar(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    isReplyMode: Boolean
) {
    var context = LocalContext.current

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(
                BorderStroke(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))
                ),
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
            ),
        trailingIcon = {
            Icon(
                painter = if (isReplyMode) {
                    painterResource(id = R.drawable.send_icon) // Altere para o ícone desejado para a resposta
                } else {
                    painterResource(id = R.drawable.send_icon) // Ícone padrão
                },
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {

                    },
                tint = Destaque2
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(252, 246, 255, 255),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(65, 57, 70, 255)
        ),
        shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
        placeholder = {
            Text(
                text = label,
                fontSize = 18.sp,
                color = Contraste2,
                maxLines = 1
            )
        },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black)
    )
}