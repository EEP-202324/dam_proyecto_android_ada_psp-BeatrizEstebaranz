package com.example.guiaorientacionapp


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.guiaorientacionapp.theme.PantallaInicio
import com.example.guiaorientacionapp.theme.PantallaLista
import com.example.guiaorientacionapp.R
import com.example.guiaorientacionapp.theme.ActividadViewModel
import com.example.guiaorientacionapp.theme.PantallaFormulario
import com.example.guiaorientacionapp.theme.PantallaListaFake

enum class PantallaOrientacion(@StringRes val title: Int) {
    Inicio(title = R.string.inicio),
    ListaFake(title = R.string.listaFake),
    Formulario(title = R.string.formulario),

}
@Composable
fun OrientacionApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            OrientacionAppBar(
                currentScreen = PantallaOrientacion.Inicio,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        NavHost(
            navController = navController,
            startDestination = PantallaOrientacion.Inicio.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = PantallaOrientacion.Inicio.name) {
                PantallaInicio(
                    navController = navController,
                    onNextButtonClicked = {
                        navController.navigate(PantallaOrientacion.ListaFake.name)
                    }
                )
            }
            composable(route = PantallaOrientacion.ListaFake.name) {
                PantallaListaFake(navController = navController)
            }
            composable(route = PantallaOrientacion.Formulario.name) {
                PantallaFormulario(navController = navController)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrientacionAppBar(
    currentScreen: PantallaOrientacion,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}