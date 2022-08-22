package com.challenge.carapp.models

import com.challenge.carapp.database.CarsTable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarsModel(
    var consList: List<String> = arrayListOf(),
    var customerPrice: Int? = null,
    var make: String? = null,
    var marketPrice: Int? = null,
    var model: String? = null,
    var prosList: List<String> = arrayListOf(),
    var rating: Int? = null,
)

fun List<CarsTable>?.copy(): List<CarsModel> {
    val carsModelList = arrayListOf<CarsModel>()
    this?.forEach {
        carsModelList.add(
            CarsModel(
                consList = it.consList,
                customerPrice = it.customerPrice,
                make = it.make,
                marketPrice = it.marketPrice,
                model = it.model,
                prosList = it.prosList,
                rating = it.rating
            )
        )
    }

    return carsModelList
}