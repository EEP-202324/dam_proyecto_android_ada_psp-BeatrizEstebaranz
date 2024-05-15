package com.example.guiaorientacionapp.api


import retrofit2.Retrofit
import retrofit2.http.GET
import com.example.guiaorientacionapp.model.Actividad
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.converter.gson.GsonConverterFactory


//.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))


private const val BASE_URL = "http://10.0.2.2:8080"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()


//        .addConverterFactory(ScalarsConverterFactory.create())
//

    interface ActividadApiService {
        @GET("/actividades")
        suspend fun obtenerActividades():  List<Actividad>
    }


    object ActividadApi {
        val retrofitService: ActividadApiService by lazy {
            retrofit.create(ActividadApiService::class.java)
        }
    }


