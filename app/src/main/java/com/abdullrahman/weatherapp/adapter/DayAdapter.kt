package com.abdullrahman.weatherapp.adapter

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.databinding.ItemDayBinding
import com.abdullrahman.weatherapp.model.DailyItem
import com.abdullrahman.weatherapp.utils.SharedPref
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class DayAdapter(
    val dayList: List<DailyItem>?,
    val context: Context,
) : RecyclerView.Adapter<DayAdapter.DayVH>() {
     var lang ="eng"
    val dayilyMap = mapOf("Monday" to "الاثنين","Tuesday" to "الثلاثاء","Wednesday" to "الاربعاء","Thursday" to "الخميس"
        ,"Friday" to "الجمعه","Saturday" to "السبت","Sunday" to "الاحد")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayVH(
            DataBindingUtil.inflate
                (
                LayoutInflater.from(parent.context),
                R.layout.item_day,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: DayVH, position: Int) {
        val resources: Resources = context.getResources()
        val sharedPref =SharedPref(context)
        val config: Configuration = resources.getConfiguration()
        if (sharedPref.getLang == "en"){
            lang = "eng"
        }
        else{
            lang ="ar"
        }

        holder.recyclerDayBinder.api = dayList?.get(position)
        set_days(holder.recyclerDayBinder.discription,position)
        val id_icon =dayList?.get(position)?.weather?.get(0)?.icon

        get_icons(id_icon,holder.recyclerDayBinder.imageView2)

    }
    fun set_days(view:TextView,position: Int)
    {
        when(position){
            0->{
                var s =""
                if(lang == "eng")
                {
                     s ="Today"+"."+dayList?.get(position)?.weather?.get(0)?.description.toString()

                }
                else{
                     s ="اليوم"+"."+dayList?.get(position)?.weather?.get(0)?.description.toString()

                }
                view.setText(s)}
            else ->{
                var s =""
                if (lang == "eng")
                 s = get_week_day(dayList?.get(position)?.dt)+"."+ dayList?.get(position)?.weather?.get(0)?.description.toString()
                else{
                    val day = dayilyMap.get(get_week_day(dayList?.get(position)?.dt))
                    if (day!= null)
                    s = dayilyMap.get(get_week_day(dayList?.get(position)?.dt))+"."+ dayList?.get(position)?.weather?.get(0)?.description.toString()
                    else
                        s = get_week_day(dayList?.get(position)?.dt)+"."+ dayList?.get(position)?.weather?.get(0)?.description.toString()

                }
                view.setText(s)

            }
        }

    }
    fun get_week_day(l:Long?):String{
        val sdf = SimpleDateFormat("EEEE")
        val dateFormat = Date(l!! *1000)
        System.out.println(dateFormat)
        val weekday: String = sdf.format(dateFormat)
        Log.e("day",weekday)
        println("loge"+weekday)
      return weekday
    }

    fun get_icons(idCode:String?,view:ImageView)
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

    override fun getItemCount() = 3

    inner class DayVH(
        val recyclerDayBinder: ItemDayBinding
    ) : RecyclerView.ViewHolder(recyclerDayBinder.root) {

    }
}