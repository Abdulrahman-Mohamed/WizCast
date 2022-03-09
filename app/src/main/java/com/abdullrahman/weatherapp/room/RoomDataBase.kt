package com.abdullrahman.weatherapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomDataBaseModel::class), version = 3, exportSchema = false)
abstract class RoomDataBase: RoomDatabase() {
    abstract fun dao():RoomDao
    companion object{
        @Volatile
        private var instance: RoomDataBase? = null

        fun getInstance(context: Context): RoomDataBase {
            return instance ?: synchronized(this) {

                instance ?: createDatabase(context).also { instance = it }
            }

        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, RoomDataBase::class.java, "WeatherDataBase"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

}


}