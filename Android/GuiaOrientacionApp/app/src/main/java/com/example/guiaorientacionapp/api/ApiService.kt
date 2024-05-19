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
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


//.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))


private const val BASE_URL = "http://10.0.2.2:8080"
//"http://10.0.2.2:8080"


// Configuración de Retrofit
// Se crea una instancia de Retrofit con la URL base del servidor.
// Se utiliza el convertidor Gson para procesar los datos JSON recibidos del servidor.
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

        @DELETE("/actividades/{actividadId}")
        suspend fun eliminarActividad(@Path("actividadId") actividadId: Long)

        @PUT("/actividades/{actividadId}")
        suspend fun editarActividad(@Path("actividadId") actividadId: Long, @Body actividad: Actividad): Response<Actividad>


    }

interface UniversidadApiService {
    @GET("/universidades")
    suspend fun obtenerUniversidades(): List<Universidad>
}



// ActividadApi y UniversidadApi son objetos singleton que proporcionan acceso al servicio Retrofit correspondiente.
// Estos objetos tienen un atributo retrofitService que permite llamar a los métodos definidos en las interfaces de servicio.

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


