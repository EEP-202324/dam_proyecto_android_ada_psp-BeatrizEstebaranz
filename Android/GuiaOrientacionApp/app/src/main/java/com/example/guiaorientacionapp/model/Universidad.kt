package com.example.guiaorientacionapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Universidad(
    val idUniversidad: Long?,
    val nombreUniversidad: String
)
