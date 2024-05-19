package com.example.guiaorientacionapp.theme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.guiaorientacionapp.model.Actividad
@Composable
fun PantallaLista(navController: NavController) {
    // Obtener el ViewModel
    val viewModel: ActividadViewModel = viewModel()

    // Lanzar el efecto automaticamente cuando el componente se inicializa
    LaunchedEffect(key1 = true) {
        viewModel.obtenerActividades()
    }

    // Obtener el estado de la UI del ViewModel(cargando, cargado o error)
    val uiState = viewModel.actividadUiState

    // variables de estado que controlan la visibilidad de dialogos y de actividad select
    var showDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedActividad by remember { mutableStateOf<Actividad?>(null) }

    // Renderizar la lista de actividades basada en el estado de la UI
    when (uiState) {
        is ActividadUiState.Loading -> {
            // Muestra un indicador de carga
            Text(text = "Cargando actividades...")
        }
        is ActividadUiState.Success -> {
            // Muestra la lista de actividades obtenidas del estado
            ActividadList(actividades = uiState.activities, onActividadClick = { actividad ->
                selectedActividad = actividad
                showDialog = true
            })
        }
        is ActividadUiState.Error -> {
            // Muestra un mensaje de error
            Text(text = "Error al cargar actividades")
        }
    }

    // Mostrar el diálogo si hay una actividad seleccionada
    if (showDialog && selectedActividad != null) { // si se ha selecccionado una actividad y esta no es nula
        ActivityDetailsDialog(
            actividad = selectedActividad!!,
            onClose = { showDialog = false },
            onDelete = {
                viewModel.eliminarActividad(selectedActividad!!.id ?: -1)
                showDialog = false // oculta el dialogo
            },
            onEdit = {
                showDialog = false
                showEditDialog = true
            }
        )
    }

    if (showEditDialog && selectedActividad != null) {
        EditActividadDialog(
            actividad = selectedActividad!!,
            onClose = { showEditDialog = false },
            onSave = { actividadActualizada ->
                viewModel.editarActividad(actividadActualizada)
                showEditDialog = false
            }
        )
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Navega a la pantalla de formulario cuando se hace clic en el botón
                navController.navigate("Formulario")
            },
            modifier = Modifier.padding(16.dp).align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Ir al formulario"
            )
        }


    }
}

@Composable // recibe lista de actividades y lambda que se ejecuta cuando se hace click
fun ActividadList(actividades: List<Actividad>, onActividadClick: (Actividad) -> Unit) {
    LazyColumn {
        items(actividades) { actividad ->
            ActividadCard(
                actividad = actividad ,
                modifier = Modifier.padding(10.dp),
                onClick = { onActividadClick(actividad) }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActividadCard(actividad: Actividad, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier,  onClick = onClick) {
        Column {
            Text(
                text = actividad.nombre ?: "",
                modifier = Modifier.padding(16.dp),

            )
        }
    }
}


@Composable
fun ActivityDetailsDialog(
    actividad: Actividad,
    onClose: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(text = "Detalles de la Actividad")
        },
        text = {
            Column {
                Text(text = actividad.nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(text = actividad.descripcion, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = actividad.universidad?.nombre_universidad ?: "No university",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        confirmButton = {
            Button(onClick = onClose) {
                Text(text = "Cerrar")
            }
        },
        dismissButton = {
            Row {
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar actividad")
                }
                IconButton(onClick = onEdit) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar actividad")
                }
            }
        }
    )
}

@Composable
fun EditActividadDialog(
    actividad: Actividad,
    onClose: () -> Unit,
    onSave: (Actividad) -> Unit
) {
    var nombre by remember { mutableStateOf(actividad.nombre) } //La variable se inicializa con el nombre de la actividad pasada como parámetro.
    var descripcion by remember { mutableStateOf(actividad.descripcion) }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(text = "Editar Actividad")
        },
        text = {
            Column {
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") }
                )
                TextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val actividadActualizada = actividad.copy(nombre = nombre, descripcion = descripcion)
                    onSave(actividadActualizada)
                }
            ) {
                Text(text = "Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text(text = "Cancelar")
            }
        }
    )
}




