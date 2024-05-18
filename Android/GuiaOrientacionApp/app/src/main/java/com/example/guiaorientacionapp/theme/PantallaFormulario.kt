package com.example.guiaorientacionapp.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.guiaorientacionapp.R
import com.example.guiaorientacionapp.model.Actividad
import com.example.guiaorientacionapp.model.Universidad
import androidx.compose.runtime.snapshots.SnapshotStateList


@Composable
fun PantallaFormulario(navController: NavController, viewModel: ActividadViewModel, backgroundImage: Painter = painterResource(id = R.drawable.img6)) {
    val (nombre, onNombreChange) = remember { mutableStateOf("") }
    val (descripcion, onDescripcionChange) = remember { mutableStateOf("") }
    val universidades = remember { mutableStateListOf<Universidad>() }
    var selectedUniversidad by remember { mutableStateOf<Universidad?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "Background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 60.dp),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = onNombreChange,
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = onDescripcionChange,
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                UniversidadDropdownMenu(
                    universidades = viewModel.universidades,
                    selectedUniversidad = selectedUniversidad,
                    onUniversidadSelected = { universidad ->
                        selectedUniversidad = universidad
                        viewModel.onUniversidadSelected(universidad)
                    }
                )

                Button(
                    onClick = {
                        Log.e("error PantallaForm", "antes de crear act")
                        val actividad = Actividad(
                            id = null,
                            nombre = nombre,
                            descripcion = descripcion,
                            universidad = selectedUniversidad,
                            id_universidad = selectedUniversidad?.id_universidad
                        )
                        Log.e("error PantallaForm", "antes de agregar act")
                        viewModel.agregarActividad(actividad)
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Hecho")
                }
            }
        }
    }
}

@Composable
fun UniversidadDropdownMenu(
    universidades: List<Universidad>,
    selectedUniversidad: Universidad?,
    onUniversidadSelected: (Universidad) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        UniversidadSelection(
            universidades = universidades,
            selectedUniversidad = selectedUniversidad,
            onUniversidadSelected = onUniversidadSelected
        )
    }
}
@Composable
fun UniversidadSelection(
    universidades: List<Universidad>,
    selectedUniversidad: Universidad?,
    onUniversidadSelected: (Universidad) -> Unit
) {
    Column {
        Text(
            text = "Universidad",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )
        Box {
            var expanded by remember { mutableStateOf(false) }
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown arrow"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                universidades.forEach { universidad ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onUniversidadSelected(universidad)
                        },
                        text = { Text(text = universidad.nombre_universidad ?: "Unknown University") } // Add Elvis operator here
                    )
                }
            }
        }
        if (selectedUniversidad != null) {
            Text(
                text = selectedUniversidad.nombre_universidad ?: "Unknown University", // Add Elvis operator here
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}