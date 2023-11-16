package br.senai.sp.jandira.costurie_app.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import br.senai.sp.jandira.costurie_app.components.ButtonSettings
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.ModalFilter
import br.senai.sp.jandira.costurie_app.function.deleteUserSQLite
import br.senai.sp.jandira.costurie_app.model.UsersTagResponse
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.viewModel.UserTagViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import coil.compose.AsyncImage

@SuppressLint("SuspiciousIndentation")
@Composable
fun SettingsScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
) {
    var context = LocalContext.current

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.retangulo_topo),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        alignment = Alignment.TopEnd
                    )
                    Row(
                        modifier = Modifier
                            .width(370.dp)
                            .padding(top = 15.dp, start = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    navController.navigate("profile")
                                }
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Spacer(modifier = Modifier.height(35.dp))

                    Spacer(modifier = Modifier.height(50.dp))

                    ButtonSettings(
                        onClick = { navController.navigate("yourAccount") }, text = "Sua conta".uppercase(), icon = painterResource(
                            id = R.drawable.icone_voltar_button
                        )
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    ButtonSettings(
                        onClick = { navController.navigate("termsAndConditions") }, text = "TERMOS E CONDIÇÕES".uppercase(), icon = painterResource(
                            id = R.drawable.icone_voltar_button
                        )
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    ButtonSettings(
                        onClick = { /*TODO*/ }, text = "AJUDA E SUPORTE".uppercase(), icon = painterResource(
                            id = R.drawable.icone_voltar_button
                        )
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    ButtonSettings(
                        onClick = { navController.navigate("about") }, text = "SOBRE".uppercase(), icon = painterResource(
                            id = R.drawable.icone_voltar_button
                        )
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    ButtonSettings(
                        onClick = {
                            val array = UserRepositorySqlite(context).findUsers()

                            val user = array[0]
                                  deleteUserSQLite(context, user.id.toInt())
                            navController.navigate("login")

                        }, text = "Sair".uppercase(), icon = painterResource(
                            id = R.drawable.icone_voltar_button
                        )
                    )
                }
            }
        }
    }
}
