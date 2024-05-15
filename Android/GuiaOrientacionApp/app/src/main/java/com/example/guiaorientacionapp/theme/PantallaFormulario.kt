package com.example.guiaorientacionapp.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import com.example.guiaorientacionapp.R
@Composable
fun PantallaFormulario(navController: NavController, backgroundImage: Painter = painterResource(id =  R.drawable.img6)) {
    val (nombre, onNombreChange) = remember { mutableStateOf("") }
    val (descripcion, onDescripcionChange) = remember { mutableStateOf("") }
    val (universidad, onUniversidadChange) = remember { mutableStateOf("") }

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
                .fillMaxWidth ()//0.9f)
                .padding(horizontal = 30.dp, vertical = 60.dp),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                // Form fields
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
                OutlinedTextField(
                    value = universidad,
                    onValueChange = onUniversidadChange,
                    label = { Text("Universidad") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Centered button
                Button(
                    onClick = {
                        // TODO: Save the newActividad instance to your database or repository
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