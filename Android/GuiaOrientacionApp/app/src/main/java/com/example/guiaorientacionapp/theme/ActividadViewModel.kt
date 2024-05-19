package com.example.guiaorientacionapp.theme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiaorientacionapp.api.ActividadApi
import com.example.guiaorientacionapp.api.ActividadApiService
import com.example.guiaorientacionapp.api.UniversidadApi
import com.example.guiaorientacionapp.model.Actividad
import com.example.guiaorientacionapp.model.Universidad
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


// Esta interfaz sellada define los diferentes estados posibles del UI relacionado con las actividades.
sealed interface ActividadUiState {
    data class Success(val activities: List<Actividad>) : ActividadUiState
    object Error : ActividadUiState
    object Loading : ActividadUiState
}


// ViewModel encargado de manejar la lógica de la pantalla de actividades.
class ActividadViewModel : ViewModel() {

    // Estado actual del UI de actividades
    var actividadUiState: ActividadUiState by mutableStateOf(ActividadUiState.Loading)
        private set

    // Estado del universidad seleccionado
    private val _selectedUniversidad = mutableStateOf<Universidad?>(null)
    val selectedUniversidad: Universidad? get() = _selectedUniversidad.value

    // Lista de universidades
    private val _universidades = mutableStateOf<List<Universidad>>(emptyList())
    val universidades: List<Universidad> get() = _universidades.value

    // Inicialización del ViewModel
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

    suspend fun getActividadById(actividadId: Long): Result<Actividad> {
        return try {
            val response = ActividadApi.retrofitService.getActividadById(actividadId)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("Failed to get actividad by ID: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun eliminarActividad(actividadId: Long) {
        viewModelScope.launch {
            try {
                ActividadApi.retrofitService.eliminarActividad(actividadId)
                obtenerActividades()  // Actualizar la lista de actividades después de la eliminación
            } catch (e: IOException) {
                Log.e("ActividadViewModel", "Error de red: ${e.message}")
            } catch (e: HttpException) {
                Log.e("ActividadViewModel", "Error HTTP: ${e.message}")
            } catch (e: Throwable) {
                Log.e("ActividadViewModel", "Error desconocido: ${e.message}")
            }
        }
    }
    fun editarActividad(actividad: Actividad) {
        viewModelScope.launch {
            try {
                ActividadApi.retrofitService.editarActividad(actividad.id ?: -1, actividad)
                obtenerActividades()
            } catch (e: IOException) {
                // Manejo del error
            } catch (e: HttpException) {
                // Manejo del error
            }
        }
    }


}

