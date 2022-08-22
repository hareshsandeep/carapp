package com.challenge.carapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.challenge.carapp.models.CarsModel

@Entity(tableName = "carsTable")
data class CarsTable(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "consList")val consList: List<String>,
    @ColumnInfo(name = "customerPrice")val customerPrice: Int?,
    @ColumnInfo(name = "make")val make: String?,
    @ColumnInfo(name = "marketPrice")val marketPrice: Int?,
    @ColumnInfo(name = "model")val model: String?,
    @ColumnInfo(name = "prosList")val prosList: List<String> = arrayListOf(),
    @ColumnInfo(name = "rating")val rating: Int?
)

fun List<CarsModel>?.copy(): List<CarsTable> {
    val carsTableList = arrayListOf<CarsTable>()
    this?.forEach {
        carsTableList.add(
            CarsTable(
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

    return carsTableList
}