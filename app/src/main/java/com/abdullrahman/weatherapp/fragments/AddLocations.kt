package com.abdullrahman.weatherapp.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.adapter.LocationAdapter
import com.abdullrahman.weatherapp.databinding.FragmentAddLocationsBinding
import com.abdullrahman.weatherapp.model.ModelLocation
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import com.abdullrahman.weatherapp.viewModel.MainViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.CoroutineScope


class AddLocations : Fragment(R.layout.fragment_add_locations) {
    val TAG = "AddLocation"
    lateinit var modelLocation: ModelLocation
    lateinit var binding: FragmentAddLocationsBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter:LocationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = Repository(requireActivity().application)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_locations, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getData()
            .observe(requireActivity(), object : Observer<List<RoomDataBaseModel>> {
                override fun onChanged(t: List<RoomDataBaseModel>?) {
                    setLocationAdapter(binding.recyclerLocations, t)

                }
            })

        binding.addPlace.setOnClickListener {
            findNavController().navigateUp()
        }
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.auto_complete)
                as AutocompleteSupportFragment
        autocompleteFragment.setActivityMode(AutocompleteActivityMode.FULLSCREEN)
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.e("addlocation",place.name.toString())
                if (place.name != null) {
                    modelLocation = ModelLocation(
                        place.id!!,
                        place.latLng?.latitude.toString(),
                        place.latLng?.longitude.toString(),
                        place.name.toString()
                    )
                } else {
                    modelLocation = ModelLocation(
                        place.id!!,
                        place.latLng?.latitude.toString(),
                        place.latLng?.longitude.toString(),
                        getAddress(place.latLng!!.latitude, place.latLng!!.longitude)
                    )
                }
                val action =
                    AddLocationsDirections.actionAddLocationsToPlaceWeeklyForcast(modelLocation)
                findNavController().navigate(action)
                Log.i(TAG, "Place: ${place.name}, ${place.id} ${place.latLng}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    fun setLocationAdapter(view: RecyclerView, list: List<RoomDataBaseModel>?) {
        adapter = LocationAdapter(list as ArrayList<RoomDataBaseModel>?, view.context)
        view.layoutManager = LinearLayoutManager(view.context)
        view.setHasFixedSize(true)
        view.adapter = adapter
        removeItemRecycler(view)
    }
    fun removeItemRecycler(rv:RecyclerView){
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.adapterPosition
                if (position != 0)
                {
                    val id = adapter.removeItem(position)
                    mainViewModel.delete(id)
                    Toast.makeText(requireActivity(), "Location deleted", Toast.LENGTH_SHORT).show()

                }else {
                    Toast.makeText(requireActivity(), "Main location", Toast.LENGTH_SHORT).show()

                    adapter.notifyDataSetChanged()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv)
    }

    private fun getAddress(lat: Double, lon: Double): String {
        val geocoder = Geocoder(context)
        var list: List<Address> = java.util.ArrayList<Address>()
        try {
            list = geocoder.getFromLocation(lat!!, lon!!, 1)
            Log.e("address", list[0].getAddressLine(0))
        } catch (e: Exception) {

            Log.e("location gps error", e.message.toString())
        }
        return list[0].getAddressLine(0)
    }
}