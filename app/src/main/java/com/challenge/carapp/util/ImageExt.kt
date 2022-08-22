package com.challenge.carapp.util

import com.challenge.carapp.R

fun String?.getImagePath(): Int =
    when(this) {
        "Range Rover" -> R.drawable.range_rover
        "Roadster" -> R.drawable.alpine_roadster
        "3300i" -> R.drawable.bmw_330i
        "GLE coupe" -> R.drawable.mercedez_benz
        else -> R.drawable.header_tacoma
    }