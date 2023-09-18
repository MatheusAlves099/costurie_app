package br.senai.sp.jandira.costurie_app.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.components.GoogleButton
import br.senai.sp.jandira.costurie_app.components.GradientButton
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2


@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    Costurie_appTheme {

        Surface (
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Box (
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.forma_tela_perfil),
                        contentDescription = "",
                        modifier = Modifier
                            .height(310.dp)
                            .width(390.dp),
                        alignment = Alignment.TopStart
                    )

                    Column (
                        modifier = Modifier
                            .height(250.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ){
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.texto_meu_perfil),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.boas_vindas).uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 24.sp
                        )

                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.texto_boas_vindas),
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(220.dp),
                            fontSize = 18.sp
                        )
                    }

                }

                Column (
                    modifier = Modifier
                        .height(480.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = R.drawable.foto_principal_main),
                        contentDescription = "",
                        modifier = Modifier
                            .height(240.dp)
                            .width(240.dp),
                        alignment = Alignment.TopStart
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    GradientButton(
                        onClick = { },
                        text = stringResource(id = R.string.texto_botao_registrar).uppercase(),
                        color1 = Destaque1,
                        color2 = Destaque2
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    GradientButton(
                        onClick = {},
                        text = stringResource(id = R.string.texto_botao_login).uppercase(),
                        color1 = Destaque1,
                        color2 = Destaque2
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}