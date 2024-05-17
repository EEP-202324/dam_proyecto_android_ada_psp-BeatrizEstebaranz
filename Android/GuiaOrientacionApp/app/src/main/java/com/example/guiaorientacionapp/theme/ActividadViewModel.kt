package com.example.guiaorientacionapp.theme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiaorientacionapp.api.ActividadApi
import com.example.guiaorientacionapp.api.UniversidadApi
import com.example.guiaorientacionapp.model.Actividad
import com.example.guiaorientacionapp.model.Universidad
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ActividadUiState {
    data class Success(val activities: List<Actividad>) : ActividadUiState
    object Error : ActividadUiState
    object Loading : ActividadUiState
}

class ActividadViewModel : ViewModel() {
    var actividadUiState: ActividadUiState by mutableStateOf(ActividadUiState.Loading)
        private set

    private val _selectedUniversidad = mutableStateOf<Universidad?>(null)
    val selectedUniversidad: Universidad? get() = _selectedUniversidad.value

    private val _universidades = mutableStateOf<List<Universidad>>(emptyList())
    val universidades: List<Universidad> get() = _universidades.value

    init {
        obtenerActividades()
        obtenerUniversidades()
    }

    fun obtenerActividades() {
        viewModelScope.launch {
            actividadUiState = ActividadUiState.Loading
            actividadUiState = try {
                val listaActividades = ActividadApi.retrofitService.obtenerActividades()
                ActividadUiState.Success(listaActividades)
            } catch (e: IOException) {
                Log.e("viewmodelLog", "Error de red: ${e.message}")
                ActividadUiState.Error
            } catch (e: HttpException) {
                Log.e("viewmodelLog", "Error HTTP: ${e.message}")
                ActividadUiState.Error
            } catch (e: Exception) {
                Log.e("viewmodelLog", "Error desconocido: ${e.message}")
                ActividadUiState.Error
            }
        }
    }

    fun obtenerUniversidades() {
        viewModelScope.launch {
            actividadUiState = ActividadUiState.Loading
            actividadUiState = try {
                val listaUniversidades = UniversidadApi.retrofitService.obtenerUniversidades()
                _universidades.value = listaUniversidades
                ActividadUiState.Success(emptyList<Actividad>())
            } catch (e: IOException) {
                Log.e("viewmodelLog", "Error de red: ${e.message}")
                ActividadUiState.Error
            } catch (e: HttpException) {
                Log.e("viewmodelLog", "Error HTTP: ${e.message}")
                ActividadUiState.Error
            } catch (e: Exception) {
                Log.e("viewmodelLog", "Error desconocido: ${e.message}")
                ActividadUiState.Error
            }
        }
    }

    fun agregarActividad(actividad: Actividad) {
        viewModelScope.launch {
            Log.e("error viewmodel", "antes de try")
            try {
                Log.e("error viewmodel", "antes de agregar act")
                ActividadApi.retrofitService.agregarActividad(actividad)
                Log.e("error viewmodel", "antes de recargar ")
                obtenerActividades()
            } catch (e: IOException) {
                Log.e("ActividadViewModel", "Network error: ${e.message}")
            } catch (e: HttpException) {
                Log.e("ActividadViewModel", "HTTP error: ${e.message}")
            }
        }
    }

    fun onUniversidadSelected(universidad: Universidad) {
        _selectedUniversidad.value = universidad
    }
}


//    Log.d("viewmodelLog", "antes de actividades")
//    val actividades = ActividadApi.retrofitService.obtenerActividades()
//    Log.d("viewmodelLog", "Respuesta de la API: $actividades")
//    var listaActividades = Json.decodeFromString<List<Actividad>>(actividades)
//    Log.d("viewmodelLog", "Lista de actividades decodificada: $listaActividades")



// var verduras = VerduleriaApi.retrofitService.getVerdurasString()
//var listaVerduras = Json.decodeFromString<List<Verdura>>(verduras)
