package com.example.guiaorientacionapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
data class Actividad(

//    @StringRes val stringResourceId: Int,
//    @DrawableRes val imageResourceId: Int

    val id: Long?,
    val nombre: String,
    val descripcion: String,
    val universidad: Universidad?

)
