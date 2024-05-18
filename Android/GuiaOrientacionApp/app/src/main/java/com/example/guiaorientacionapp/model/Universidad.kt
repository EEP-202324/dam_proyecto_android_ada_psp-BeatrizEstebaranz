package com.example.guiaorientacionapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Universidad(
    val id_universidad: Long?,
    val nombre_universidad: String
)
