package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import br.senai.sp.jandira.costurie_app.ui.theme.ShapeButton
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel2


@Composable
fun ModalTags2(
    color1: Color,
    color2: Color,
    viewModel: UserViewModel2,

    ) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Button(
        onClick = {
            isDialogOpen = true
        },
        modifier = Modifier
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        color1,
                        color2
                    )
                ),
                shape = ShapeButton.large,
            )
            .height(37.dp)
            .width(115.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp
        )
    ) {
        Text(
            text = stringResource(id = R.string.texto_button_tag),
            fontSize = 11.sp,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
    }

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpen = false
            },
            title = {
                IconButton(
                    onClick = { isDialogOpen = false },

                    ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp)
                    )
                }
            },
            text = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Define o número de colunas por linha
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(viewModel.tags!!.toList()) { tag ->
                        GradientButtonTag(
                            onClick = { /*TODO*/ },
                            color1 = Destaque1,
                            color2 = Destaque2,
                            tagId = tag.id,
                            text = tag.nome_tag,
                            textColor =  Color(168, 155, 255, 255)
                        )
                    }
                }

            },
            containerColor = colorResource(id = R.color.principal_2),
            confirmButton = {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                ) {
                    Text(text = "Fechar", color = Color.Transparent)
                }
            }
        )

    }
}


//@Preview(showBackground = true)
//@Composable
//fun ModalExamplePreview() {
//    ModalTags2(color1 = Destaque1, color2 = Destaque2)
//}