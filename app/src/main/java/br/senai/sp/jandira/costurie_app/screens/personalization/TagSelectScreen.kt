package br.senai.sp.jandira.costurie_app.screens.personalization


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.Storage
import br.senai.sp.jandira.costurie_app.components.CustomOutlinedTextField2
import br.senai.sp.jandira.costurie_app.components.GradientButtonTag
import br.senai.sp.jandira.costurie_app.components.TagColorViewModel
import br.senai.sp.jandira.costurie_app.model.TagResponse
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.model.TagsResponse
import br.senai.sp.jandira.costurie_app.repository.TagsRepository
import br.senai.sp.jandira.costurie_app.repository.UserRepository
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque1
import br.senai.sp.jandira.costurie_app.ui.theme.Destaque2
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagSelectScreen(
    //viewModel: UserViewModel,
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    localStorage: Storage
) {

    val brush = Brush.horizontalGradient(listOf(Destaque1, Destaque2))

    var pesquisaState by remember {
        mutableStateOf("")
    }

    var tagsList by remember() {
        mutableStateOf(listOf<TagsResponse>())
    }

    var context = LocalContext.current

    val tagsRepository = TagsRepository()

    val userRepository = UserRepository()

    val array = UserRepositorySqlite(context).findUsers()

    val user = array[0]

    fun filtro(text: String): List<TagsResponse> {
        lifecycleScope.launch {
            var result = tagsRepository.getAllTags(user.token).body()!!.data
            var emptyArray = emptyList<TagsResponse>()
            tagsList = if (result.isEmpty()) { emptyArray } else { result }
        }
        var newList: List<TagsResponse> = tagsList.filter {
            it.nome_tag.contains(text, ignoreCase = true)
        }
        return newList
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    val viewModel: TagColorViewModel = viewModel()

    var tagsArray = mutableListOf<TagResponseId>()

    Costurie_appTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.navigate("profileType")
                        },

                        ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                        )
                    }
                    Button(
                        onClick = {
                            if (tagsArray.isEmpty()) {
                                Toast.makeText(context, "É necessário escolher ao menos uma tag!", Toast.LENGTH_SHORT).show()
                            } else {
                                lifecycleScope.launch {

                                    userRepository.updateUserTags(
                                        user.id.toInt(),
                                        user.token,
                                        tagsArray)

                                    localStorage.salvarValor(context, "0", "currentScreen")
                                    navController.navigate("home")
                                }
                            }
                        },
                        modifier = Modifier
                            .size(45.dp)
                            .background(
                                brush = brush,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }
                }//row
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 35.dp, vertical = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(

                        text = stringResource(id = R.string.texto_tag_de_servico).uppercase(),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(id = R.string.descricao_tag_de_servico),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CustomOutlinedTextField2(
                        value = pesquisaState,
                        onValueChange = {
                            pesquisaState = it
                            filtro(pesquisaState)
                        },
                        label = stringResource(id = R.string.tag_de_servico_label),
                        borderColor = Color.Transparent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = rememberLazyGridState(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filtro(pesquisaState)) {
                            GradientButtonTag(
                                onClick = {
                                        isClicked = !isClicked
                                    if (isClicked) {
                                        viewModel.setTagColor(it.id, Destaque1, Destaque2)
                                        viewModel.setTagTextColor(it.id, Color.White, Color.White)
                                        if (!tagsArray.contains(TagResponseId(it.id))){
                                            tagsArray.add(TagResponseId(it.id))
                                        }
                                    } else {
                                        viewModel.setTagColor(it.id, Color.Transparent, Color.Transparent)
                                        viewModel.setTagTextColor(it.id, Destaque1, Destaque2)
                                        if (tagsArray.contains(TagResponseId(it.id))) {
                                            tagsArray.remove(TagResponseId(it.id))
                                        }
                                    }
                                },
                                tagId = it.id,
                                color1 = Destaque1,
                                color2 = Destaque2,
                                text = it.nome_tag,
                                textColor = Color(168, 155, 255, 255),
                            )
                        }
                    }

                }
            }
        }
    }
}


//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun PreviewProfileTypeScreen() {
//    TagSelectScreen()
//}