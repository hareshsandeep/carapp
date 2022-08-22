package com.challenge.carapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(StringListConverter::class)
@Database(entities = [CarsTable::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}