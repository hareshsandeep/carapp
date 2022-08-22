package com.challenge.carapp.util

import com.challenge.carapp.adapters.ANY_MAKE
import com.challenge.carapp.adapters.ANY_MODEL
import com.challenge.carapp.models.CarsModel

fun List<CarsModel>?.getCarMakeInfoList(): ArrayList<String> {
    val makeList = arrayListOf<String>()
    makeList.add(ANY_MAKE)
    this?.forEach { item ->
        if (!item.make.isNullOrEmpty()) {
            item.make?.let { it -> makeList.add(it) }
        }
    }
    return makeList
}

fun List<CarsModel>?.getCarModelInfoList(): ArrayList<String> {
    val modelList = arrayListOf<String>()
    modelList.add(ANY_MODEL)
    this?.forEach { item ->
        if (!item.model.isNullOrEmpty()) {
            item.model?.let { it -> modelList.add(it) }
        }
    }
    return modelList
}