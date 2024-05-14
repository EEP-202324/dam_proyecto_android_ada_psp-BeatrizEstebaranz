package com.example.guiaorientacionapp.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiaorientacionapp.api.ActividadApi
import com.example.guiaorientacionapp.model.Actividad
import kotlinx.coroutines.launch
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
                val listaActividades = ActividadApi.retrofitService.obtenerActividades()
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

}

