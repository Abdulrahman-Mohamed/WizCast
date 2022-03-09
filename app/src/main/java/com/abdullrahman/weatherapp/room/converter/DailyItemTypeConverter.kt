package com.abdullrahman.weatherapp.room.converter

import androidx.room.TypeConverter
import com.abdullrahman.weatherapp.model.DailyItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DailyItemTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromDailyItemList(value: MutableList<DailyItem>): String {
            val gson = Gson()
            val type = object : TypeToken<MutableList<DailyItem>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toDailyItemList(value: String): MutableList<DailyItem> {
            val gson = Gson()
            val type = object : TypeToken<MutableList<DailyItem>>() {}.type
            return gson.fromJson(value, type)
        }
    }
}