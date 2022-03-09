package com.abdullrahman.weatherapp.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.adapter.WeeklyAdapter
import com.abdullrahman.weatherapp.databinding.FragmentPlaceWeeklyForcastBinding
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import com.abdullrahman.weatherapp.viewModel.PlacesViewModel
import com.abdullrahman.weatherapp.viewModel.PlacesViewModelFactory
import com.abdullrahman.weatherapp.viewModel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaceWeeklyForcast : Fragment() {
    lateinit var navController: NavController
    lateinit var binding: FragmentPlaceWeeklyForcastBinding
    lateinit var placesVM: PlacesViewModel
    var roomModel: RoomDataBaseModel? = null
    val args: PlaceWeeklyForcastArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val repository = Repository(requireActivity().application)
        placesVM = ViewModelProvider(requireActivity(), PlacesViewModelFactory(repository)).get(
            PlacesViewModel::class.java
        )
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_place_weekly_forcast,
            container,
            false
        )
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val model = args.place
        Log.e("PlaceWeekly", model.toString())
        placesVM.getAddedState().observe(requireActivity(), object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t!!) {
                    placesVM.getAddedState().removeObserver(this)
                    placesVM.added.postValue(false)
                    navController.navigateUp()
                }
            }

        })

        placesVM
            .getPlace(
                model.lat.toDouble(),
                model.lng.toDouble(),
                model.id,
                "eng",
                "metric",
                "minutely",
                model.address
            )
            .observe(requireActivity()) { room ->
                if (room != null) {
                    val liveData = placesVM.GetAllData()
                    liveData?.observeOnce {
                        if (it != null) {
                            for (item in it!!) {
                                if (room?.id == item.id) {
                                    binding.addLayout.visibility = View.GONE
                                }else{binding.addLayout.visibility = View.VISIBLE}
                            }
                            roomModel = room
                            Log.e("PlaceWeekly", roomModel.toString())

                            if (roomModel != null) {
                                setFLoatButton()
                                setRecycler()
                            }
                        }
                    }
                }
            }


    }

    private fun setFLoatButton() {
        binding.floatingActionButton.setOnClickListener {
            placesVM.setPlaceDb(roomModel!!)
        }
    }

    private fun setRecycler() {
        if (binding.forecastRecycler.adapter == null) {
            binding.forecastRecycler.apply {
                layoutManager =
                    LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = WeeklyAdapter(requireActivity(), roomModel!!.daily)
            }
        }
    }

}

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}
