package com.abdullrahman.weatherapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.databinding.FragmentMainBinding
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import com.abdullrahman.weatherapp.utils.GPS_location
import com.abdullrahman.weatherapp.utils.SharedPref
import com.abdullrahman.weatherapp.viewModel.ApiViewModel
import com.abdullrahman.weatherapp.viewModel.ApiViewModelFactory
import com.abdullrahman.weatherapp.viewModel.ViewModelFactory
import java.lang.Exception
import java.util.ArrayList


class MainFragment(val db:RoomDataBaseModel) : Fragment(R.layout.fragment_main) {
    lateinit var binding:FragmentMainBinding

    lateinit var apiViewModel: ApiViewModel
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        apiViewModel = ViewModelProvider(
            this,
            ApiViewModelFactory(db)
        ).get(ApiViewModel::class.java)

        binding= DataBindingUtil.inflate(inflater,
            R.layout.fragment_main,container,false)
        binding.progressBarCyclic.visibility = View.VISIBLE
        binding.mainFragmentLayout.visibility = View.GONE
        binding.api = apiViewModel
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setUnite()
        getAddress()
        btn_menu()
        btn_addLocation()
        apiViewModel.progressEnd.observe(requireActivity(), Observer {
            if (it){
            binding.progressBarCyclic.visibility = View.GONE
                binding.mainFragmentLayout.visibility = View.VISIBLE

                apiViewModel.progressEnd.postValue(false)
        }})



    }

    private fun setUnite() {
        val shared = SharedPref(requireActivity())
        if (shared.getUnit.equals("metric" ,true))
            binding.unite.text = requireActivity().resources.getString(R.string.unite_metric)
        else
            binding.unite.text = requireActivity().resources.getString(R.string.unite_imperial)

    }

    fun getAddress()
    {
        binding.tvCity.isSelected = true
        binding.tvCity.text = db.address

    }
    private fun btn_addLocation()
    {
        binding.addPlace.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_addLocations)
        }
    }

    /*"this function is for initialize menu btn to go to settings screen"*/
    private fun btn_menu()
    {
        binding.btnMenu.setOnClickListener {
            val popMenu = PopupMenu(this.requireContext(), it);
            popMenu.menuInflater.inflate(R.menu.menus_main,popMenu.menu)
            popMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {item->
                when(item.itemId)
                {
                    R.id.settings ->{
                        findNavController().navigate(R.id.action_viewPagerFragment_to_settings2)
                    }
                }
                true
            })
            popMenu.show()
        }    }



}