package com.example.guiaorientacionapp.theme


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.guiaorientacionapp.model.Actividad
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory // Importa el convertidor de Gson
import com.example.guiaorientacionapp.api.ApiService


// Crea una instancia de Retrofit con el convertidor de Gson
val retrofit = Retrofit.Builder()
    .baseUrl("URL_DE_TU_API")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// Crea una instancia del servicio ApiService
val apiService = retrofit.create(ApiService::class.java)

@Composable
fun PantallaLista(navController: NavController) {
    // Obtener la lista de actividades de la base de datos
    val actividades = remember { mutableStateOf<List<Actividad>>(emptyList()) }

    // Realizar la solicitud para obtener las actividades
    LaunchedEffect(key1 = true) {
        obtenerActividadesDesdeApi { listaActividades ->
            actividades.value = listaActividades
        }
    }

    ActividadList(actividades.value)
}

@Composable
fun ActividadCard(actividad: Actividad, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {

            Text(
                text = actividad.nombre,
                modifier = Modifier.padding(16.dp),

            )
            Text(
                text = actividad.descripcion,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun ActividadList(actividades: List<Actividad>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(actividades.size) { index ->
            val actividad = actividades[index]
            ActividadCard(
                actividad = actividad,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


// Función para obtener las actividades desde la API
fun obtenerActividadesDesdeApi(onSuccess: (List<Actividad>) -> Unit) {
    // Llama al método obtenerActividades del ApiService
    apiService.obtenerActividades().enqueue(object : Callback<List<Actividad>> {
        override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
            if (response.isSuccessful) {
                // Si la solicitud fue exitosa, llama a onSuccess con la lista de actividades obtenida
                onSuccess(response.body() ?: emptyList())
            } else {
                // Si hubo un error en la solicitud, muestra un mensaje de error
                // Esto es opcional y puedes manejar los errores de acuerdo a tus necesidades
                // Por ejemplo, puedes lanzar un error aquí o mostrar un mensaje al usuario
                // según lo que prefieras
                // Por ahora, simplemente imprimiremos un mensaje de error en la consola
                Log.e("ApiService", "Error al obtener las actividades: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
            // Si hubo un error en la comunicación con el servidor, muestra un mensaje de error
            // Esto es opcional y puedes manejar los errores de acuerdo a tus necesidades
            // Por ejemplo, puedes lanzar un error aquí o mostrar un mensaje al usuario
            // según lo que prefieras
            // Por ahora, simplemente imprimiremos un mensaje de error en la consola
            Log.e("ApiService", "Error de red al obtener las actividades", t)
        }
    })
}
@Preview
@Composable
fun PantallaListaPreview() {

}