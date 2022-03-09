package com.abdullrahman.weatherapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val basedUrl = "https://api.openweathermap.org"
    fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(basedUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}