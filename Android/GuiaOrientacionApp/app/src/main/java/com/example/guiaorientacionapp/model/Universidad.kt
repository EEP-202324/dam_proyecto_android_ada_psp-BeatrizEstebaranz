package com.example.guiaorientacionapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Universidad(
    val idUniversidad: Long?,
    val nombre_universidad: String
)
