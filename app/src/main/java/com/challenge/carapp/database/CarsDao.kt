package com.challenge.carapp.database

import androidx.room.*



@Dao
interface CarDao {
    @Query("SELECT * FROM carsTable")
    fun getCarDetailsList(): List<CarsTable>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(carsList: List<CarsTable> )

    @Query("DELETE FROM carsTable")
    fun flushTable()
}

class StringListConverter {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}