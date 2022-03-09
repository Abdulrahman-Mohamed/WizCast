package com.abdullrahman.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import kotlinx.coroutines.*

class PlacesViewModel(val repository: Repository):ViewModel() {
    lateinit var database:LiveData<List<RoomDataBaseModel>>
    var place: MutableLiveData<RoomDataBaseModel> = MutableLiveData<RoomDataBaseModel>()
    var added : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    fun getPlace(lat: Double,lon: Double,id:String,lang:String,units:String,exclude:String,address:String):MutableLiveData<RoomDataBaseModel>{
       val job = CoroutineScope(Dispatchers.IO).launch {
            val dummy_place = repository.getPlace(lat, lon, id, lang, units, exclude, address)
            place.postValue(dummy_place)
        }
        runBlocking {
            job.join()

        }
        return place
    }
    fun GetAllData():LiveData<List<RoomDataBaseModel>>{
        database = repository.getDataLocal()
        return database

    }
     fun setPlaceDb(roomModel:RoomDataBaseModel){
        viewModelScope.launch {
            repository.setPlace(roomModel)
            added.postValue(true)
        }
    }
    fun getAddedState(): MutableLiveData<Boolean> {
        return added
    }

}