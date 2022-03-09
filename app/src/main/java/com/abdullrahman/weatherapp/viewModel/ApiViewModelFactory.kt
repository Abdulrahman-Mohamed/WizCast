package com.abdullrahman.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.room.RoomDataBaseModel

class ApiViewModelFactory(val db: RoomDataBaseModel): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ApiViewModel::class.java))
        {
            ApiViewModel(this.db) as T
        }
        else{
            throw IllegalArgumentException("ViewModel Not Found")

        }
    }
}