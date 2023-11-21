package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2

@Composable

fun ModalDeleteMessage(
    lifecycleScope: LifecycleCoroutineScope,
    localStorage: Storage,
    navController: NavController
) {

    var isDialogOpen by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.trash_icon),
        contentDescription = "",
        modifier = Modifier
            .size(40.dp)
            .clickable {
                isDialogOpen = true
            }
    )

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpen = false
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.text_modal_exit),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row (
                        modifier = Modifier
                            .width(220.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        GradientButtonModal(
                            onClick = { navController.navigate("login") },
                            text = stringResource(id = R.string.text_yes).uppercase(),
                            color1 = Destaque1,
                            color2 = Destaque2
                        )

                        GradientButtonModal(
                            onClick = { navController.navigate("settings") },
                            text = stringResource(id = R.string.text_no).uppercase(),
                            color1 = Destaque1,
                            color2 = Destaque2
                        )
                    }
                }
            },
            containerColor = Color.White,
            confirmButton = {}
        )
    }
}