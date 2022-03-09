package com.abdullrahman.weatherapp.utils

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.Lifecycle
import com.abdullrahman.weatherapp.viewModel.LocationViewModel
import com.abdullrahman.weatherapp.model.ModelLocation

import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class GPS_location(val context: Activity,lifecycle: Lifecycle,val locationVM:LocationViewModel) {
    val PERMISSION = 99
    private lateinit var locationCallback: LocationCallback
    lateinit var locationRequist: LocationRequest
    lateinit var fusedLocaationProvider: FusedLocationProviderClient
    lateinit var currentLocation: Location


    init {

        createLocationRequest()
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(p0: LocationResult) {
//                p0 ?: return
//                var location = p0.lastLocation
//            }}
        fusedLocaationProvider = LocationServices.getFusedLocationProviderClient(context)
        val job = GlobalScope.launch(Dispatchers.IO){
            fusedLocaationProvider.lastLocation.addOnSuccessListener {

                currentLocation = it
                Log.e("location",currentLocation.latitude.toString()+" & "+currentLocation.longitude.toString())
                val geocoder= Geocoder(context)
                var   list:List<Address> = ArrayList<Address>()
                try {
                    list = geocoder.getFromLocation(it.latitude,it.longitude,1)

                }catch (e: Exception){

                Log.e("location gps error", e.message.toString())
                }
                locationVM.addItemModel(
                    ModelLocation(
                    "current",
                    currentLocation.latitude.toString(),
                    currentLocation.longitude.toString(),
                    list[0].getAddressLine(0))
                )
            }
        }
        runBlocking { job.join() }
      }
    fun createLocationRequest() {
        locationRequist = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 10000

        }
//    public fun gps() {
//        fusedLocaationProvider = LocationServices.getFusedLocationProviderClient(context)
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocaationProvider.lastLocation.addOnSuccessListener {
//                currentLocation = it
//            }
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(,
//                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                    PERMISSION
//                )
//            }
//        }
//    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        when (requestCode) {
//            PERMISSION -> {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    gps()
//                } else {
//                    Toast.makeText(this, "permission not graanted", Toast.LENGTH_LONG).show()
//                    finish()
//                }
//            }
//            else -> Toast.makeText(this, "not here", Toast.LENGTH_LONG).show()
//        }
//
//
//    }
}}