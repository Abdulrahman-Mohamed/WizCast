package com.abdullrahman.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

class SharedPref(context: Context) {
    companion object {
        private lateinit var pref: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private const val PREF_NAME = "SHARED_PREFERENCE"
        const val FirstTimeKey = "FirstTime"
        const val LangKey = "LANG"
        const val UNITS_SHARED_PREF = "UNITS"

    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }
    fun setFirstTime(state:Boolean)
    {
        editor.putBoolean(FirstTimeKey,state)
    }
    val FirstTimeState:Boolean?
        get() = pref.getBoolean(FirstTimeKey,true)

    fun setLang(Lang: String) {
        editor.putString(LangKey, Lang)
        editor.apply()
    }

    val getLang: String?
        get() = pref.getString(LangKey, "en")

    fun setUnit(unit: String) {
        editor.putString(UNITS_SHARED_PREF, unit)
        editor.apply()
    }

    val getUnit: String?
        get() = pref.getString(UNITS_SHARED_PREF, "metric")
    fun setList(key: String?, list: List<RoomDataBaseModel>?) {
        val gson = Gson()
        val json: String = gson.toJson(list)
        set(key, json)
    }

    operator fun set(key: String?, value: String?) {
        if (pref != null) {
            val prefsEditor: SharedPreferences.Editor = editor
            prefsEditor.putString(key, value)
            prefsEditor.commit()
        }
    }
    fun getModelList(key: String?): ArrayList<RoomDataBaseModel>? {
        if (pref != null) {
            val gson = Gson()
            val companyList: ArrayList<RoomDataBaseModel>
            val string: String = pref.getString(key, null).toString()
            if (string != "null") {
                val type: Type = object : TypeToken<ArrayList<RoomDataBaseModel?>?>() {}.type
                companyList = gson.fromJson<ArrayList<RoomDataBaseModel>>(string, type)
                return companyList
            }
        }
        return null
    }
}