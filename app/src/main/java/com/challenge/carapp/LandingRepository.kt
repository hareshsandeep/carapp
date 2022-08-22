package com.challenge.carapp

import android.app.Application
import android.util.Log
import com.challenge.carapp.models.CarsModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException

class LandingRepository(
    private val application: Application
) {
    private val classTag = LandingRepository::class.simpleName

    fun fetchCarsList() : List<CarsModel>? {
        return try {
            val stringList = application.assets.open("car_list.json").bufferedReader().use { it.readText() }

            val moshi: Moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, CarsModel::class.java)
            val adapter: JsonAdapter<List<CarsModel>?> = moshi.adapter(listType)
            adapter.fromJson(stringList)
        } catch (exception: IOException) {
            Log.e(classTag, "Failed to retrieve json from asset folder $exception")
            null
        }
    }
}