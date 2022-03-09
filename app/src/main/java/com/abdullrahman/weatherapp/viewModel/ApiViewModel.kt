package com.abdullrahman.weatherapp.viewModel

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.model.Current
import com.abdullrahman.weatherapp.model.DailyItem
import com.abdullrahman.weatherapp.model.HourlyItem
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.util.ArrayList

class ApiViewModel(val model:RoomDataBaseModel):ViewModel() {
    val timezone:MutableLiveData<Long?> = MutableLiveData(model.timezoneOffset!!)
    var  current:MutableLiveData<Current?> = MutableLiveData<Current?>(model.current!!)
     var hourly: MutableLiveData<List<HourlyItem?>> = MutableLiveData<List<HourlyItem?>>(model.hourly)
    var daily: MutableLiveData<List<DailyItem?>> = MutableLiveData<List<DailyItem?>>(model.daily)
    var progressEnd:MutableLiveData<Boolean> = MutableLiveData(false)
   // var model: MutableLiveData<ArrayList<RoomDataBaseModel>> =MutableLiveData<ArrayList<RoomDataBaseModel>>()

    init {
//        current .postValue(model.current!!)
//        hourly .postValue(model.hourly)
//        daily .postValue(model.daily)
    }



    fun getDayList():MutableLiveData<List<DailyItem?>>?
    {
        return daily
    }
    fun getHourList():MutableLiveData<List<HourlyItem?>>?
    {
        return hourly
    }
    fun get_current():MutableLiveData<Current?>{
        Log.e("viewModel",current.value?.temp?.toInt().toString())

        return current
    }


}