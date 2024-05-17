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
import androidx.compose.ui.res.painterResource
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
import androidx.lifecycle.viewmodel.compose.viewModel


enum class PantallaOrientacion(@StringRes val title: Int) {
    Inicio(title = R.string.inicio),
    Lista(title = R.string.Lista),
    ListaFake(title = R.string.listaFake),
    Formulario(title = R.string.formulario),

}

@Composable
fun OrientacionApp() {
    val navController = rememberNavController()
    val viewModel: ActividadViewModel = viewModel() // Obtener una instancia del ViewModel

    Scaffold(
        topBar = {
            OrientacionAppBar(
                currentScreen = getCurrentScreen(navController),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
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
                        navController.navigate(PantallaOrientacion.Lista.name)
                    }
                )
            }
            composable(route = PantallaOrientacion.Lista.name) {
                PantallaLista(navController = navController)
            }
            composable(route = PantallaOrientacion.Formulario.name) {
                PantallaFormulario(
                    navController = navController,
                    viewModel = viewModel,
                    backgroundImage = painterResource(id = R.drawable.img6)
                )
            }
        }
    }
}


@Composable
fun getCurrentScreen(navController: NavController): PantallaOrientacion {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    return when (currentDestination?.route) {
        PantallaOrientacion.Inicio.name -> PantallaOrientacion.Inicio
        PantallaOrientacion.Lista.name -> PantallaOrientacion.Lista
        PantallaOrientacion.Formulario.name -> PantallaOrientacion.Formulario
        PantallaOrientacion.ListaFake.name -> PantallaOrientacion.ListaFake
        else -> PantallaOrientacion.Inicio
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
    if (currentScreen != PantallaOrientacion.Inicio) {
        TopAppBar(
            title = {
                when (currentScreen) {
                    PantallaOrientacion.Inicio -> Text(stringResource(R.string.inicio))
                    PantallaOrientacion.Lista -> Text(stringResource(R.string.listaBar))
                    PantallaOrientacion.Formulario -> Text(stringResource(R.string.formularioBar))
                    PantallaOrientacion.ListaFake -> Text(stringResource(R.string.listaBar))
                }
            },
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
                } else {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
        )
    }
}