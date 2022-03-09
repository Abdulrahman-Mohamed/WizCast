package com.abdullrahman.weatherapp.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ResponseApi {
    @GET("/data/2.5/onecall")
    suspend fun getResponse(
        @Query("lat") lat:Double,
        @Query("lon")   lon:Double,
        @Query("lang")  lang:String,
        @Query("units") units: String,
        @Query("exclude") exclude: String,
        @Query("appid") appid: String): Response<com.abdullrahman.weatherapp.model.Response>
}