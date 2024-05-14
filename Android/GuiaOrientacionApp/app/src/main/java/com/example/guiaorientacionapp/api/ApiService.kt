package com.example.guiaorientacionapp.api

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import com.example.guiaorientacionapp.model.Actividad
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Url
import kotlinx.serialization.json.Json

import retrofit2.converter.gson.GsonConverterFactory


    private const val BASE_URL = "https://10.0.2.2:8080"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()


    interface ActividadApiService {
        @GET("/actividades")
        suspend fun obtenerActividades(): List<Actividad>
    }


    object ActividadApi {
        val retrofitService: ActividadApiService by lazy {
            retrofit.create(ActividadApiService::class.java)
        }
    }


