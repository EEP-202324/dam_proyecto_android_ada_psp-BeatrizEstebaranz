package com.example.guiaorientacionapp.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.guiaorientacionapp.model.Actividad

@Composable
fun PantallaLista(navController: NavController) {
    // Obtener el ViewModel
    val viewModel: ActividadViewModel = viewModel()

    // Lanzar el efecto cuando el componente se inicializa
    LaunchedEffect(key1 = true) {
        viewModel.obtenerActividades()
    }

    // Obtener el estado de la UI del ViewModel
    val uiState = viewModel.actividadUiState

    // Renderizar la lista de actividades basada en el estado de la UI
    when (uiState) {
        is ActividadUiState.Loading -> {
            // Muestra un indicador de carga
            Text(text = "Cargando actividades...")
        }
        is ActividadUiState.Success -> {
            // Muestra la lista de actividades obtenidas del estado
            ActividadList(actividades = uiState.activities)
        }
        is ActividadUiState.Error -> {
            // Muestra un mensaje de error
            Text(text = "Error al cargar actividades")
        }
    }
}

@Composable
fun ActividadList(actividades: List<Actividad>) {
    LazyColumn {
        items(actividades) { actividad ->
            ActividadCard(actividad = actividad)
        }
    }
}


@Composable
fun ActividadCard(actividad: Actividad, modifier: Modifier = Modifier) {
    androidx.compose.material3.Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(actividad.imageResourceId),
                contentDescription = stringResource(actividad.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            androidx.compose.material3.Text(
                text = LocalContext.current.getString(actividad.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}


