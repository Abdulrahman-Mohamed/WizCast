package com.abdullrahman.weatherapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullrahman.weatherapp.consts.Constants.WEATHERKEY
import com.abdullrahman.weatherapp.model.Current
import com.abdullrahman.weatherapp.model.DailyItem
import com.abdullrahman.weatherapp.model.HourlyItem
import com.abdullrahman.weatherapp.retrofit.RetrofitHelper
import com.abdullrahman.weatherapp.room.RoomDataBase
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Response
import java.util.*

class Repository(val application: Application) {

    private val localDatabase =RoomDataBase.getInstance(application).dao()

    val ResponseApi = RetrofitHelper.getInstance()
        .create(com.abdullrahman.weatherapp.retrofit.ResponseApi::class.java)
    lateinit var response: Response<com.abdullrahman.weatherapp.model.Response>
    lateinit var hourly: MutableLiveData<List<HourlyItem?>>
    var daily: MutableLiveData<List<DailyItem?>>
    var current: MutableLiveData<Current?> = MutableLiveData<Current?>()
    lateinit var timeZone: String

    init {
        hourly = MutableLiveData<List<HourlyItem?>>()
        daily = MutableLiveData<List<DailyItem?>>()

    }

   suspend fun setLocation(lat: Double,lon: Double,id:String,lang:String,units:String,exclude:String,address:String)
    {
        response = ResponseApi.getResponse(
            lat,lon,lang,units,exclude,
            "a4536bb03376e418fe19aaf1d7ea0628"
        )
        if (response.isSuccessful) {
            Log.e("repository", "success")

            daily.postValue(response.body()?.daily)
            hourly.postValue(response.body()?.hourly)
            current.postValue(response.body()?.current)
            val timeZone =response.body()?.timezone
            val timeZoneOffset = response.body()?.timezoneOffset
            val placeModel =RoomDataBaseModel(id, response.body()?.current,address,timeZone, timeZoneOffset,response.body()?.daily,lon,response.body()!!.hourly,lat)
            localDatabase.insert(placeModel)
        }
    }

    fun getDataLocal():LiveData<List<RoomDataBaseModel>>{
        return localDatabase.getAllPlaces()
    }
    suspend fun setPlace(roomDataBaseModel: RoomDataBaseModel)
    {
        localDatabase.insert(roomDataBaseModel)
    }
    suspend fun getPlace(lat: Double,lon: Double,id:String,lang:String,units:String,exclude:String,address:String):RoomDataBaseModel?
    {
        response = ResponseApi.getResponse(
            lat,lon,lang,units,exclude,
            WEATHERKEY
        )
        if (response.isSuccessful) {
            Log.e("repository", "success")

            daily.postValue(response.body()?.daily)
            hourly.postValue(response.body()?.hourly)
            current.postValue(response.body()?.current)
            val timeZone =response.body()?.timezone
            val timeZoneOffset = response.body()?.timezoneOffset
            val placeModel =RoomDataBaseModel(id, response.body()?.current,address,timeZone, timeZoneOffset,response.body()?.daily,lon,response.body()!!.hourly,lat)
            return placeModel
        }
        return null
    }
    suspend fun updateData(lat: Double,lon: Double,id:String,lang:String,units:String,exclude:String,address: String)
    {
        response = ResponseApi.getResponse(
            lat,lon,lang,units,exclude,
            WEATHERKEY
        )
        if (response.isSuccessful) {
            Log.e("repository", "success")

            daily?.postValue(response.body()?.daily)
            hourly.postValue(response.body()?.hourly)
            current.postValue(response.body()?.current)
            val timeZone =response.body()?.timezone
            val timeZoneOffset = response.body()?.timezoneOffset
            val placeModel =RoomDataBaseModel(id, current.value,address,timeZone, timeZoneOffset,daily?.value,lon,hourly.value,lat)
            localDatabase.update(placeModel)
        }
    }
    suspend fun updateAll(list:List<RoomDataBaseModel>,units: String,lang: String)
    {
        for (item in list) {

            response = ResponseApi.getResponse(
                item.lat!!, item.lon!!, lang, units, "minutely",
                "a4536bb03376e418fe19aaf1d7ea0628"
            )
            if (response.isSuccessful) {
                Log.e("repository_update", "success")

//                daily.postValue(response.body()?.daily)
//                hourly.postValue(response.body()?.hourly)
//                current.postValue(response.body()?.current)
                val timeZone = response.body()?.timezone
                val timeZoneOffset = response.body()?.timezoneOffset
                val placeModel = RoomDataBaseModel(
                    item.id,
                    response.body()?.current,
                    item.address,
                    timeZone,
                    timeZoneOffset,
                    response.body()?.daily,
                    item.lon,
                    response.body()?.hourly,
                    item.lat
                )
                Log.e("update repository",placeModel.toString())
                localDatabase.update(placeModel)
            }
        }
    }
    suspend fun deletePlace(id:String)
    {
        localDatabase.delete(id)
    }
    suspend fun deleteAll(){
        localDatabase.deleteAll()
    }
    suspend fun getApiData(lat:Double,lon:Double,lang:String,units:String,exclude:String) {
        response = ResponseApi.getResponse(
            lat,lon,lang,units,exclude,
            WEATHERKEY
        )
        if (response.isSuccessful) {
            Log.e("repository", response.body()?.daily?.size.toString())

            daily?.postValue(response.body()?.daily)
            hourly.postValue(response.body()?.hourly)
            current.postValue(response.body()?.current)


        }
    }
}