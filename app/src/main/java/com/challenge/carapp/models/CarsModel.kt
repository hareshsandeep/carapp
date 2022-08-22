package com.challenge.carapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarsModel(
    var consList: List<String> = arrayListOf(),
    var customerPrice: Int? = null,
    var make: String? = null,
    var marketPrice: Int? = null,
    var model: String? = null,
    var prosList: List<String> = arrayListOf(),
    var rating: Int? = null
)