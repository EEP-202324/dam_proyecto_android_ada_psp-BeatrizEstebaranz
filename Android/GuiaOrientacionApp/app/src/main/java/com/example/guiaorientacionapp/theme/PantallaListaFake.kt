package com.example.guiaorientacionapp.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.guiaorientacionapp.data.Datasource
import com.example.guiaorientacionapp.model.Actividad

@Composable
fun PantallaListaFake(navController: NavController) {
    val actividadesList = Datasource().loadActividades()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Lista de Actividades")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Navega a la pantalla de formulario cuando se hace clic en el botón
                navController.navigate("Formulario")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Ir al formulario")
        }
        Spacer(modifier = Modifier.height(16.dp))
        ActividadListfake(actividadesList = actividadesList)
    }
}

@Composable
fun ActividadListfake(actividadesList: List<Actividad>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(actividadesList) { actividad ->
            ActividadCardFake(
                actividad = actividad,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ActividadCardFake(actividad: Actividad, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(id = actividad.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = actividad.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
