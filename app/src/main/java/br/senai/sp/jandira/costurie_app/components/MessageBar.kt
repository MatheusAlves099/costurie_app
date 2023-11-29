package br.senai.sp.jandira.costurie_app.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MessageBar(
    value: String,
    onValueChange: (String) -> Unit,
    navController: NavController,
) {
    var context = LocalContext.current

    var mensagemState by remember {
        mutableStateOf(value)
    }

    TextField(
        value = mensagemState,
        onValueChange = {
            mensagemState = it
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
            Row {
                Image(
                    painter = painterResource(id = R.drawable.icon_add_image),
                    contentDescription = "",
                    Modifier
                        .size(30.dp)
                        .clickable {
                            navController.navigate("PictureScreen")
                        }
                )
                
                Spacer(modifier = Modifier.width(6.dp))
                
                Icon(
                    painter = painterResource(id = R.drawable.send_icon), // Altere para o ícone desejado para a resposta,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onValueChange(mensagemState)
                            mensagemState = ""
                        },
                    tint = Destaque2
                )
            }

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
                text = mensagemState,
                fontSize = 18.sp,
                color = Contraste2,
                maxLines = 1
            )
        },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black)
    )
}