package br.senai.sp.jandira.costurie_app.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.LifecycleCoroutineScope
import br.senai.sp.jandira.costurie_app.MainActivity
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.repository.LocationRepository
import br.senai.sp.jandira.costurie_app.repository.LoginRepository
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2
import br.senai.sp.jandira.costurie_app.viewModel.EstadoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownEstado(
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: EstadoViewModel,
    estado: String = "",
    onEstadoSelected: (String) -> Unit,
) {

    val context = LocalContext.current

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val estados = remember { mutableStateListOf<String>() }

    var estado by remember {
        mutableStateOf(estado)
    }

    var textFieldSize by remember {
        mutableStateOf(androidx.compose.ui.geometry.Size.Zero)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    fun loadEstados(
        viewModel: EstadoViewModel
    ) {
        val locationRepository = LocationRepository()
        lifecycleScope.launch {
            val response = locationRepository.getEstados()

            if (response.isSuccessful) {
                val estadosResponse = response.body()
                estadosResponse?.forEach { estado ->
                    estados.add(estado.Nome)
                    viewModel.estadoSelecionado = estado.Nome
                }
            } else {
                val errorBody = response.errorBody()?.string()

                Log.e(
                    MainActivity::class.java.simpleName,
                    "Erro durante carregar os estados: $errorBody"
                )
                Toast.makeText(context, "Erro durante carregar os estados", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        loadEstados(viewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    isExpanded = false
                }
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                androidx.compose.material.TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .background(Color(252,246,255), shape = RoundedCornerShape(15.dp))
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = estado!!,
                    onValueChange = {
                        estado = it
                        isExpanded = true
                    },
                    placeholder = {
                        if (estado.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.label_dropdown_localizacao),
                                fontSize = 18.sp,
                                color = Contraste2,
                                maxLines = 1
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { isExpanded = !isExpanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp)
                        .background(Color.Transparent),
                    elevation = 15.dp
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .background(Color.Transparent),
                    ) {

                        if (estado!!.isNotEmpty()) {
                            items(
                                estados.filter {
                                    it.lowercase()
                                        .contains(estado!!.lowercase()) || it.lowercase()
                                        .contains("others")
                                }
                                    .sorted()
                            ) {
                                CategoryItemsEstado(title = it) { title ->
                                    estado = title
                                    onEstadoSelected(title)
                                    viewModel.estadoSelecionado = title
                                    isExpanded = false
                                }
                            }
                        } else {
                            items(
                                estados.sorted()
                            ) {
                                CategoryItemsEstado(title = it) { title ->
                                    estado = title
                                    onEstadoSelected(title)
                                    viewModel.estadoSelecionado = title
                                    isExpanded = false
                                }
                            }
                        }

                    }

                }
            }

        }

    }
}

@Composable
fun CategoryItemsEstado(
    title: String,
    onSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(252, 246, 255))
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, color = Color.Black, fontSize = 16.sp)
    }
}
