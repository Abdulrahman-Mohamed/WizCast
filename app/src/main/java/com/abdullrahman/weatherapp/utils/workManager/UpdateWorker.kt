package com.abdullrahman.weatherapp.utils.workManager

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.abdullrahman.weatherapp.consts.Constants
import com.abdullrahman.weatherapp.consts.Constants.WorkerKey
import com.abdullrahman.weatherapp.model.Response
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.retrofit.RetrofitHelper
import com.abdullrahman.weatherapp.room.RoomDataBase
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import com.abdullrahman.weatherapp.utils.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.ArrayList

class UpdateWorker( val appContext: Context, workerParams: WorkerParameters):Worker(appContext,workerParams) {
    val shared = SharedPref(appContext)
    override fun doWork(): Result {
        val data = shared.getModelList(WorkerKey)
        if (data!= null)
        UpdateWork(data)
        return Result.success()
    }

    fun UpdateWork(data:ArrayList<RoomDataBaseModel>?){
         val localDatabase = RoomDataBase.getInstance(appContext).dao()
        //val repository =Repository(app)
        val ResponseApi = RetrofitHelper.getInstance()
            .create(com.abdullrahman.weatherapp.retrofit.ResponseApi::class.java)
        lateinit var response: retrofit2.Response<Response>
        if (data !=null)
        for (d in data)
        {
            CoroutineScope(Dispatchers.IO).launch {
                response = ResponseApi.getResponse(
                    d.lat!!,d.lon!!,shared.getLang.toString(),shared.getUnit.toString(), "minutely",
                    Constants.WEATHERKEY
                )
                if (response.isSuccessful) {
                    Log.e("repository", "success")

                    val timeZone =response.body()?.timezone
                    val timeZoneOffset = response.body()?.timezoneOffset
                    val placeModel =RoomDataBaseModel(d.id, response.body()?.current,d.address,timeZone, timeZoneOffset,response.body()?.daily,d.lon,response.body()?.hourly,d.lat)
                    localDatabase.update(placeModel)
                }

              //  repository.updateData(d.lat!!,d.lon!!,d.id,shared.getLang.toString(),shared.getUnit.toString(), "minutely",d.address!!)
            }
        }
    }
}