package com.example.guiaorientacionapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Renderiza la aplicación principal de oantalla orienracion
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
