package com.abdullrahman.weatherapp.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.abdullrahman.weatherapp.consts.Constants.WorkerKey
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import com.abdullrahman.weatherapp.utils.workManager.UpdateWorker
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(val repository: Repository) :ViewModel(){
    lateinit var model: LiveData<List<RoomDataBaseModel>>

    suspend fun setLocation(lat: Double,lon: Double,id:String,lang:String,units:String,exclude:String,address:String){
        repository.setLocation(lat,lon,id,lang,units,exclude,address)
    }

     fun getData(): LiveData<List<RoomDataBaseModel>> {
        model = repository.getDataLocal()
         Log.e("MainViewModel", repository.getDataLocal().value.toString())
        return repository.getDataLocal()
    }
    suspend fun updateData(lat: Double,lon: Double,id:String,lang:String,units:String,exclude:String,address: String)
    {
        repository.updateData(lat,lon,id,lang,units,exclude,address)
    }
   suspend fun updateAll(list:List<RoomDataBaseModel>,units: String,lang: String){

            repository.updateAll(list,units,lang)

    }
     fun delete(id:String)
    {
        viewModelScope.launch {
            repository.deletePlace(id)

        }
    }


}