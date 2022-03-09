package com.abdullrahman.weatherapp.room.converter

import androidx.room.TypeConverter
import com.abdullrahman.weatherapp.model.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherItemTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromWeatherItemList(value: MutableList<WeatherItem>): String {
            val gson = Gson()
            val type = object : TypeToken<MutableList<WeatherItem>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toWeatherItemList(value: String): MutableList<WeatherItem> {
            val gson = Gson()
            val type = object : TypeToken<MutableList<WeatherItem>>() {}.type
            return gson.fromJson(value, type)
        }
    }
}