package com.example.guiaorientacionapp.data

import com.example.guiaorientacionapp.R
import com.example.guiaorientacionapp.model.Actividad

class Datasource() {
    fun loadActividades(): List<Actividad> {
        return listOf<Actividad>(
            Actividad(R.string.act1, R.drawable.img1),
            Actividad(R.string.act2, R.drawable.img2),
            Actividad(R.string.act3, R.drawable.img3),
            Actividad(R.string.act4, R.drawable.img4),
            Actividad(R.string.act5, R.drawable.img5))

    }
}
