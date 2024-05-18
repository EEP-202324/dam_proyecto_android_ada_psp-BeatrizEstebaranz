package com.example.guiaorientacionapp.api


import retrofit2.Retrofit
import retrofit2.http.GET
import com.example.guiaorientacionapp.model.Actividad
import com.example.guiaorientacionapp.model.Universidad
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


//.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))


private const val BASE_URL = "http://10.0.2.2:8080"
//"http://10.0.2.2:8080"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()


//        .addConverterFactory(ScalarsConverterFactory.create())
//

    interface ActividadApiService {
        @GET("/actividades")
        suspend fun obtenerActividades():  List<Actividad>

        @POST("/actividades/")
        suspend fun agregarActividad(@Body actividad: Actividad): Actividad


        @GET("actividades/{actividadId}")
        suspend fun getActividadById(@Path("actividadId") actividadId: Long): Response<Actividad>


    }

interface UniversidadApiService {
    @GET("/universidades")
    suspend fun obtenerUniversidades(): List<Universidad>
}

    object ActividadApi {
        val retrofitService: ActividadApiService by lazy {
            retrofit.create(ActividadApiService::class.java)
        }
    }

    object UniversidadApi{
        val retrofitService: UniversidadApiService by lazy {
            retrofit.create(UniversidadApiService::class.java)
        }
    }


