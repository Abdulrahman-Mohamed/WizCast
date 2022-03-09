package com.abdullrahman.weatherapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.databinding.ItemHourBinding
import com.abdullrahman.weatherapp.model.HourlyItem
import com.abdullrahman.weatherapp.utils.SharedPref
import java.util.*

class HourAdapter(
    val list: List<HourlyItem>?,
    val context: Context, val offset: Long
) : RecyclerView.Adapter<HourAdapter.HourVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HourVH(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hour,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: HourVH, position: Int) {
        setUnite(holder)
        holder.recyclerHourItem.api = list?.get(position)
        holder.recyclerHourItem.timezoneOffset = offset
        val id_icon = list?.get(position)?.weather?.get(0)?.icon
        Log.e("icon", id_icon.toString())
        get_icons(id_icon, holder.recyclerHourItem.imageView)
    }

    private fun setUnite(holder: HourVH) {
        val shared = SharedPref(context)
        if (shared.getUnit.equals("metric", true))
            holder.recyclerHourItem.sign.text = context.resources.getString(R.string.unite_metric)
        else
            holder.recyclerHourItem.sign.text = context.resources.getString(R.string.unite_imperial)

    }

    fun get_icons(idCode: String?, view: ImageView) {
        when (idCode) {
            "01d" -> {
                view.setImageResource(R.drawable.ic_sunny)
            }//clear sky
            "01n" -> {
                view.setImageResource(R.drawable.ic_nitght)
            }
            "02d" -> {
                view.setImageResource(R.drawable.ic_day_cloudy)
            }//few clouds
            "02n" -> {
                view.setImageResource(R.drawable.ic_nitght)
            }
            "03d" -> {
                view.setImageResource(R.drawable.ic_cloud)
            }//scattered clouds
            "03n" -> {
                view.setImageResource(R.drawable.ic_cloud)
            }
            "04d" -> {
                view.setImageResource(R.drawable.ic_cloud)
            } //broken clouds
            "04n" -> {
                view.setImageResource(R.drawable.ic_cloud)
            }
            "09d" -> {
                view.setImageResource(R.drawable.ic_rainy)
            }//shower rain
            "09n" -> {
                view.setImageResource(R.drawable.ic_rainy)
            }
            "10d" -> {
                view.setImageResource(R.drawable.ic_rainy)
            }//	rain
            "10n" -> {
                view.setImageResource(R.drawable.ic_rainy)
            }
            "11d" -> {
                view.setImageResource(R.drawable.ic_lightning)
            }//	thunderstorm
            "11n" -> {
                view.setImageResource(R.drawable.ic_lightning)
            }
            "13d" -> {
                view.setImageResource(R.drawable.ic_snow)
            }//snow
            "13n" -> {
                view.setImageResource(R.drawable.ic_snow)
            }
            "50d" -> {
                view.setImageResource(R.drawable.ic_wind)
            } //mist
            "50n" -> {
                view.setImageResource(R.drawable.ic_wind)
            }
            else -> {
                Log.e("icon error", "error icon")
                view.setImageResource(R.drawable.ic_cloud)
            }
        }
    }

    override fun getItemCount() = list!!.size - 24

    inner class HourVH(val recyclerHourItem: ItemHourBinding) :
        RecyclerView.ViewHolder(recyclerHourItem.root) {}

}