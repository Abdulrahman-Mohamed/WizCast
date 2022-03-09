package com.abdullrahman.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.abdullrahman.weatherapp.consts.Constants.APIKEY
import com.abdullrahman.weatherapp.repository.Repository
import com.abdullrahman.weatherapp.utils.GPS_location
import com.abdullrahman.weatherapp.utils.SharedPref
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val sharedPref =SharedPref(this)
        changeLocale(sharedPref.getLang!!)
        // Initialize the SDK
        Places.initialize(applicationContext, APIKEY)

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)



    }

    private fun changeLocale(lang:String) {
        val config = resources.configuration
        val lang = lang // your language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            this.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
