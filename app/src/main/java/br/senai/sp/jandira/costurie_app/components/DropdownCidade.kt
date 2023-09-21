package br.senai.sp.jandira.costurie_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.costurie_app.R
import br.senai.sp.jandira.costurie_app.ui.theme.Contraste2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCidade() {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var cidade by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(67.dp)
            .background(
                color = Color.White,
                //colorResource(id = R.color.principal_2),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            modifier = Modifier.background(
                color = Color.White
            )
        ) {

            TextField(
                value = cidade,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .background(
                        color = Color.White
                    )

            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(
                    color = Color.White
                )
            ) {
                DropdownMenuItem(
                    text = { Text("Barueri", fontSize = 15.sp, color = Contraste2) },
                    onClick = {
                        cidade = "Barueri"
                        isExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("São Paulo", fontSize = 15.sp, color = Contraste2) },
                    onClick = {
                        cidade = "São Paulo"
                        isExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Rio", fontSize = 15.sp, color = Contraste2) },
                    onClick = {
                        cidade = "Rio"
                        isExpanded = false
                    }
                )
            }

        }
    }


}

@Preview
@Composable
fun DropdownCidadePreview() {
    DropdownCidade()
}