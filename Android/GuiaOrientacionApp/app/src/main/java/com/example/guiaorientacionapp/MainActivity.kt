package com.example.guiaorientacionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Configuración del gráfico de navegación
            OrientacionApp()
        }
    }
}

@Preview
@Composable
fun PreviewMainActivity() {
    // Renderiza la vista previa de MainActivity
    MainActivity()
}
