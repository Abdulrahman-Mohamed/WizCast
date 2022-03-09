package com.abdullrahman.weatherapp.settings

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.fragments.observeOnce
import com.abdullrahman.weatherapp.utils.SharedPref
import com.abdullrahman.weatherapp.viewModel.MainViewModel
import kotlinx.coroutines.*
import java.security.Provider
import java.util.*

class Settings:PreferenceFragmentCompat() {
    private lateinit var unitListPreference: ListPreference
    private lateinit var languageListPreference: ListPreference
    lateinit var sharedPref: SharedPref
    lateinit var mainViewModel:MainViewModel
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.prefrence)
        sharedPref = SharedPref(requireContext())
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        unitListPreference = findPreference("UNIT_SYSTEM")!!
        languageListPreference = findPreference("LANGUAGE")!!
        unitListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            pref.edit().putString("UNIT_SYSTEM",newValue.toString()).apply()
            sharedPref.setUnit(newValue.toString())

                udateData()


            true
        }
        languageListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            pref.edit().putString("LANGUAGE",newValue.toString()).apply()
            sharedPref.setLang(newValue.toString())
                udateData()

                changeLocale(newValue.toString())


            true
        }
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
            requireActivity().createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun udateData(){
        mainViewModel.getData().observeOnce {

            val job =CoroutineScope(Dispatchers.IO).launch {
                if (it != null)
                    mainViewModel.updateAll(it,sharedPref.getUnit!!,sharedPref.getLang!!)

            }
            runBlocking { job.join()
                findNavController().navigate(R.id.viewPagerFragment)
            }

        }
    }
}