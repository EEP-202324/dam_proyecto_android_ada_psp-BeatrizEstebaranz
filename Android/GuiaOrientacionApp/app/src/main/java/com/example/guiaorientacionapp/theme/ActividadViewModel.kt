package com.example.guiaorientacionapp.theme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiaorientacionapp.api.ActividadApi
import com.example.guiaorientacionapp.model.Actividad
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

sealed interface ActividadUiState {
    data class Success(val activities: List<Actividad>) : ActividadUiState
    object Error : ActividadUiState
    object Loading : ActividadUiState
}

class ActividadViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var actividadUiState: ActividadUiState by mutableStateOf(ActividadUiState.Loading)
        private set

    /**
     * Call obtenerActividades() on init so we can display status immediately.
     */
    init {
        obtenerActividades()
    }



    /**
     * Obtains activities information from the API service and updates the [Actividad] [List] [MutableList].
     */
    fun obtenerActividades() {
        viewModelScope.launch {
            actividadUiState = ActividadUiState.Loading
            actividadUiState = try {
                Log.d("viewmodelLog", "antes de actividades")
                val actividades = ActividadApi.retrofitService.obtenerActividades()
                Log.d("viewmodelLog", actividades)
                var listaActividades = Json.decodeFromString<List<Actividad>>(actividades)
                ActividadUiState.Success(
                    listaActividades
                )
            } catch (e: IOException) {
                 ActividadUiState.Error
            } catch (e: HttpException) {
                ActividadUiState.Error
            }
        }
    }

    // var verduras = VerduleriaApi.retrofitService.getVerdurasString()
    //var listaVerduras = Json.decodeFromString<List<Verdura>>(verduras)

}

