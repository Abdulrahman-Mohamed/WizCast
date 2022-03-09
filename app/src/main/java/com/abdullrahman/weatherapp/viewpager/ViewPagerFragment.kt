package com.abdullrahman.weatherapp.viewpager

import android.Manifest
import android.app.Application
import android.content.Context
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import androidx.work.*
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.consts.Constants
import com.abdullrahman.weatherapp.databinding.FragmentViewPagerBinding
import com.abdullrahman.weatherapp.fragments.MainFragment
import com.abdullrahman.weatherapp.fragments.observeOnce
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.utils.GPS_location
import com.abdullrahman.weatherapp.utils.SharedPref
import com.abdullrahman.weatherapp.utils.notification.NotificationNormal
import com.abdullrahman.weatherapp.utils.workManager.UpdateWorker
import com.abdullrahman.weatherapp.viewModel.ApiViewModel
import com.abdullrahman.weatherapp.viewModel.LocationViewModel
import com.abdullrahman.weatherapp.viewModel.MainViewModel
import com.abdullrahman.weatherapp.viewModel.ViewModelFactory
import com.abdullrahman.weatherapp.viewpager.viewPagerAnimation.CubeInRotationTransformation
import com.abdullrahman.weatherapp.viewpager.viewPagerAnimation.FanTransformation
import com.abdullrahman.weatherapp.viewpager.viewPagerAnimation.VirtecalFlip
import com.asknilesh.CubeOutRotationTransformation
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    val PERMISSION = 99
    lateinit var gps: GPS_location
    lateinit var binding: FragmentViewPagerBinding
    lateinit var mainVM: MainViewModel
    lateinit var locationVM: LocationViewModel
    lateinit var fragmentsList: ArrayList<Fragment>
    lateinit var fragment: MainFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = Repository(requireActivity().application)
        mainVM = ViewModelProvider(
            requireActivity(), ViewModelFactory(repository)
        ).get(MainViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_pager, container, false
        )
        binding.layout.visibility = View.GONE
        binding.progressBa.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = SharedPref(requireActivity())

        fragmentsList = ArrayList()
        locationVM = ViewModelProvider(
            this,
            SavedStateViewModelFactory(activity?.application, this)
        ).get(LocationViewModel::class.java)

        mainVM.getData().observeOnce { data ->
            if (data != null && data?.size != 0)
            {
                sharedPref.setList(Constants.WorkerKey,data)
                startWork()


                Log.e("ViewPager", "Data" + data.size.toString())
                for (f in data) {
                    fragment = MainFragment(f)
                    Log.e("ViewPager", "Data" + f.id)
                    fragmentsList.add(fragment)
                //    get_background(f.current?.weather?.get(0)?.icon)

                }
                Log.e("ViewPager", "list" + fragmentsList.size)

                binding.viewPager.adapter = ViewPagerAdapter(
                    fragmentsList,
                    requireActivity().supportFragmentManager,
                    lifecycle
                )
                binding.viewPager.setPageTransformer(VirtecalFlip())
                binding.viewPager.setSaveEnabled(false)

                binding.dotsIndicator.setViewPager2( binding.viewPager)

            } else {
                Log.e("ViewPager", "location set")
                get_location()
                locationVM.getItemModel()?.observe(requireActivity(), Observer {
                    val job = lifecycleScope.launch(Dispatchers.IO) {
                        if (it != null) {
                            Log.e("ViewPager", "location set" + it.address)
                            mainVM.setLocation(
                                it!!.lat.toDouble(),
                                it!!.lng.toDouble(),
                                "current",
                                "eng",
                                "metric",
                                "minutely",
                                it.address
                            )
                        }
                    }
                    runBlocking {
                        job.join()
                        binding.viewPager.adapter = ViewPagerAdapter(
                            fragmentsList,
                            requireActivity().supportFragmentManager,
                            lifecycle
                        )

                        binding.viewPager.setPageTransformer(VirtecalFlip())
                        binding.viewPager.setSaveEnabled(false)
                        binding.dotsIndicator.setViewPager2( binding.viewPager)

                    }
                })
            }
            binding.progressBa.visibility = View.GONE
            binding.layout.visibility = View.VISIBLE

        }
    }
    fun startWork(): UUID {

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicWorkRequest = PeriodicWorkRequestBuilder<UpdateWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "UpdateWeather",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
        WorkManager.getInstance(requireContext()).getWorkInfosForUniqueWorkLiveData("UpdateWeather")
            .observe(requireActivity(), Observer {
                Log.e("worker",it[0].state.name)
                if (it[0].state.name.equals("RUNNING")){
                val notification = NotificationNormal(requireContext(),"Updating Data")
                notification.notifyBtn()}
            })
        return periodicWorkRequest.id

    }
    fun get_background(icon: String?) {
        if (icon?.contains("d", true)!!) {
            binding.layout.setBackgroundResource(R.drawable.gradient_day)
        } else {
            binding.layout.setBackgroundResource(R.drawable.gradient_night)
        }
    }

    private fun get_location() {
        if (checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("ViewPager", "permotion set")

            gps = GPS_location(requireActivity(), lifecycle, locationVM)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION
                )
                Log.e("ViewPager", "permotion check")

            }
            Log.e("ViewPager", "permotion not check")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    get_location()
                } else {
                    activity?.finish()
                }
            }
        }
    }

}