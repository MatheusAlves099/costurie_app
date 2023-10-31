package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R

@Composable
fun ModalEditDeletePublication() {
    var isDialogOpen by remember { mutableStateOf(false) }

    val context = LocalContext.current


    Image(
        painter = painterResource(id = R.drawable.baseline_more_horiz_24),
        contentDescription = "",
        modifier = Modifier
            .size(45.dp)
            .clickable {
                isDialogOpen = true
            }
    )

    if (isDialogOpen) {
        Box(modifier = Modifier.fillMaxSize().offset(30.dp, 60.dp)) {
            AlertDialog(
                onDismissRequest = {
                    isDialogOpen = false
                },
                modifier = Modifier
                    .padding(16.dp)
                    .width(165.dp)
                    .height(74.dp),
                title = {

                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Editar Publicação",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Deletar Publicação",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }

                },
                containerColor = Color.White,
                confirmButton = {}
            )
        }
    }
}