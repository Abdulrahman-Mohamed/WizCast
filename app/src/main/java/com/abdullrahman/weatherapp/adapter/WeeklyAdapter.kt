package com.abdullrahman.weatherapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.databinding.ItemWeekForecastBinding
import com.abdullrahman.weatherapp.model.DailyItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeeklyAdapter(val context: Context, val list: List<DailyItem?>?) :
    RecyclerView.Adapter<WeeklyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_week_forecast,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerForcast.api = list?.get(position)
        set_days(holder.recyclerForcast.discription,position)
        val id_icon =list?.get(position)?.weather?.get(0)?.icon

        get_icons(id_icon,holder.recyclerForcast.imageView2)
    }
    fun set_days(view: TextView, position: Int)
    {
        when(position){
            0->{
                val s ="Today"+"."+list?.get(position)?.weather?.get(0)?.main.toString()
                view.setText(s)}
            else ->{
                val s = get_week_day(list?.get(position)?.dt)+"."+ list?.get(position)?.weather?.get(0)?.main.toString()
                view.setText( s)

            }
        }

    }
    fun get_week_day(l:Long?):String{
        val sdf = SimpleDateFormat("EEEE")
        val dateFormat = Date(l!! *1000)
        System.out.println(dateFormat)
        val weekday: String = sdf.format(dateFormat)
        Log.e("day",weekday)
        println(weekday)
        return weekday
    }

    fun get_icons(idCode:String?,view: ImageView)
    {
        when (idCode){
            "01d"->{ view.setImageResource(R.drawable.ic_sunny)  }//clear sky
            "01n"->{ view.setImageResource(R.drawable.ic_nitght)}
            "02d"->{ view.setImageResource( R.drawable.ic_day_cloudy)}//few clouds
            "02n"->{view.setImageResource( R.drawable.ic_nitght)}
            "03d"->{view.setImageResource(R.drawable.ic_cloud)}//scattered clouds
            "03n"->{view.setImageResource( R.drawable.ic_cloud)}
            "04d"->{view.setImageResource( R.drawable.ic_cloud)} //broken clouds
            "04n"->{view.setImageResource( R.drawable.ic_cloud)}
            "09d"->{view.setImageResource( R.drawable.ic_rainy)}//shower rain
            "09n"->{view.setImageResource(R.drawable.ic_rainy)}
            "10d"->{view.setImageResource(R.drawable.ic_rainy)}//	rain
            "10n"->{view.setImageResource(R.drawable.ic_rainy)}
            "11d"->{view.setImageResource( R.drawable.ic_lightning)}//	thunderstorm
            "11n"->{view.setImageResource( R.drawable.ic_lightning)}
            "13d"->{view.setImageResource( R.drawable.ic_snow)}//snow
            "13n"->{view.setImageResource( R.drawable.ic_snow)}
            "50d"->{view.setImageResource( R.drawable.ic_wind)} //mist
            "50n"->{view.setImageResource( R.drawable.ic_wind)}
            else->{
                Log.e("icon error","error icon")
                view.setImageResource( R.drawable.ic_cloud)
            }
        }
    }


    override fun getItemCount() = list!!.size

    inner class ViewHolder(val recyclerForcast: ItemWeekForecastBinding) :
        RecyclerView.ViewHolder(recyclerForcast.root)
}