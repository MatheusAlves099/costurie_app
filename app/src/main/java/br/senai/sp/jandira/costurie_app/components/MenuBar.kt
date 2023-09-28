package br.senai.sp.jandira.costurie_app.components


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBar() {
    Costurie_appTheme {

        val items = listOf(
            BottomnavigationBarItem(
                title = "home",
                selected = "Home",
                unselected = painterResource(id = R.drawable.home_bar),
                hasNews = false
            ),
            BottomnavigationBarItem(
                title = "serviços",
                selected = "Serviços",
                unselected = painterResource(id = R.drawable.servicos_bar),
                hasNews = false
            ),
            BottomnavigationBarItem(
                title = "publicar",
                selected = "Publicar",
                unselected = painterResource(id = R.drawable.center_button_bar),
                hasNews = false
            ),
            BottomnavigationBarItem(
                title = "conversas",
                selected = "Conversas",
                unselected = painterResource(id = R.drawable.conversas_bar),
                hasNews = false
            ),
            BottomnavigationBarItem(
                title = "perfil",
                selected = "Perfil",
                unselected = painterResource(id = R.drawable.perfil_bar),
                hasNews = false
            )
        )

        var selectedIdexItem by rememberSaveable {
            mutableStateOf(0)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Scaffold(
                containerColor = Color.Blue,
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier
                            .height(80.dp)
                            .padding(start = 17.dp, end = 17.dp, bottom = 15.dp)
                            .clip(shape = RoundedCornerShape(15.dp)),
                        containerColor = Color.White,
                        contentColor = Color.Transparent
                    ) {
                        items.forEachIndexed{index, item ->
                            NavigationBarItem(
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.White
                                ),
                                selected = selectedIdexItem == index,
                                onClick = {
                                          selectedIdexItem = index
                                          // navController.navigate(item.title)
                                },
                                icon = {
                                    BadgedBox(
                                        badge = {

                                        }
                                    ) {
                                        if(selectedIdexItem == index) {
                                            TextMenuBar(text = item.selected)
                                        } else {
                                            if(index == 2) {
                                                Image(painter = item.unselected,
                                                    modifier = Modifier.size(70.dp),
                                                    contentDescription = item.title)
                                            } else {
                                                Image(painter = item.unselected,
                                                    modifier = Modifier.size(16.dp),
                                                    contentDescription = item.title)
                                            }
                                        }
                                    }
                                })
                        }
                    }
                }
            ) {

            }
        }
    }
}

data class BottomnavigationBarItem(
    val title: String,
    val selected: String,
    val unselected: Painter,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@Preview(showBackground = true)
@Composable
fun PreviewMenuBar() {
    MenuBar()
}