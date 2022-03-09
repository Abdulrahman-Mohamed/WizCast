package com.abdullrahman.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abdullrahman.weatherapp.model.ModelLocation

class LocationViewModel(val savedStateHandle: SavedStateHandle):ViewModel() {
    private val key = "location_Model"
    val items: MutableLiveData<ModelLocation.CREATOR> = savedStateHandle.getLiveData(key,
        ModelLocation
    )
    val list:MutableLiveData<ModelLocation> = MutableLiveData<ModelLocation>()

    fun addItemModel(models: ModelLocation?){
        list.value = models
        savedStateHandle.set(key,models)
    }



    fun getItemModel():MutableLiveData<ModelLocation>?
    {
       return list
    }
}