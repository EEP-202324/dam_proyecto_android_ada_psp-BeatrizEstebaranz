package com.example.guiaorientacionapp


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guiaorientacionapp.theme.PantallaInicio
import com.example.guiaorientacionapp.theme.PantallaLista
import com.example.guiaorientacionapp.R

@Composable
fun OrientacionApp() {
    val navController = rememberNavController()

    // Configuración del gráfico de navegación
    NavHost(navController, startDestination = "inicio") {
        composable("inicio") {
            PantallaInicio(navController)
        }
        composable("listado") {
            PantallaLista(navController)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrientacionAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(),
        modifier = modifier.fillMaxWidth(),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}