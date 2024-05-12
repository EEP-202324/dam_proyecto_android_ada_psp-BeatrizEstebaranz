package com.example.guiaorientacionapp.api

import com.example.guiaorientacionapp.model.Actividad
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/actividades/{actividadId}")
    fun obtenerDetallesActividad(@Path("actividadId") actividadId: Long): Call<Actividad>

    @GET("/actividades")
    fun obtenerActividades(): Call<List<Actividad>>

    @POST("/actividades/")
    fun agregarActividad(@Body actividad: Actividad): Call<Actividad>

    @PUT("/actividades/{actividadId}")
    fun editarActividad(
        @Path("actividadId") actividadId: Long,
        @Body actividadActualizada: Actividad
    ): Call<Actividad>

    @DELETE("/actividades/{actividadId}")
    fun borrarActividad(@Path("actividadId") actividadId: Long): Call<Void>
}