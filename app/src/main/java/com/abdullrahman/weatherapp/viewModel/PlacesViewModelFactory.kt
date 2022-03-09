package com.abdullrahman.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdullrahman.weatherapp.repository.Repository

class PlacesViewModelFactory(val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PlacesViewModel::class.java))
        {
            PlacesViewModel(this.repository) as T
        }
        else{
            throw IllegalArgumentException("ViewModel Not Found")

        }
    }
}