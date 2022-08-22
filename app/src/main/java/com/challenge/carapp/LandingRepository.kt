package com.challenge.carapp

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.challenge.carapp.database.CarDao
import com.challenge.carapp.database.CarDatabase
import com.challenge.carapp.database.copy
import com.challenge.carapp.models.CarsModel
import com.challenge.carapp.models.copy
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException

class LandingRepository(
    private val application: Application
) {
    private val classTag = LandingRepository::class.simpleName

    private val database = Room.databaseBuilder(
        application,
        CarDatabase::class.java, "database-car"
    ).build()

    private val carTable: CarDao
        get() = database.carDao()

    fun getCarsList() : List<CarsModel>? {
        val databaseList = carTable.getCarDetailsList()
        if (!databaseList.isNullOrEmpty()) {
            return databaseList.copy()
        }

        return try {
            val stringList = application.assets.open("car_list.json").bufferedReader().use { it.readText() }
            val moshi: Moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, CarsModel::class.java)
            val adapter: JsonAdapter<List<CarsModel>?> = moshi.adapter(listType)
            val carsModelList = adapter.fromJson(stringList)
            // insert into the table
            carTable.insert(carsModelList.copy())
            return carsModelList
        } catch (exception: IOException) {
            Log.e(classTag, "Failed to retrieve json from asset folder $exception")
            null
        }
    }
}