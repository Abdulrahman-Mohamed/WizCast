package com.abdullrahman.weatherapp.utils

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.adapter.DayAdapter
import com.abdullrahman.weatherapp.adapter.HourAdapter
import com.abdullrahman.weatherapp.adapter.LocationAdapter
import com.abdullrahman.weatherapp.model.Current
import com.abdullrahman.weatherapp.model.DailyItem
import com.abdullrahman.weatherapp.model.HourlyItem
import com.abdullrahman.weatherapp.room.RoomDataBaseModel
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("image")
fun loadImage(view:ImageView, url:Int){
    Glide.with(view)
        .load(url)
        .into(view)
}
@BindingAdapter("text_trim")
fun trim_text(view:TextView,text:String)
{
    Log.e("utils",text)
    val list = text.split(",")
    Log.e("utils",list.toString())
    view.text = text
}
@BindingAdapter("adapter")
fun setAdapter(view:RecyclerView,list: List<DailyItem>?)
{
    view.layoutManager=LinearLayoutManager(view.context)
    view.setHasFixedSize(true)
    view.adapter = DayAdapter(list, view.context)
}
@BindingAdapter("adapter_horizontal","offset")
fun setAdapterHorizontal(view:RecyclerView,list: List<HourlyItem>?,offset:Long,)
{
    view.layoutManager=LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)
    view.setHasFixedSize(true)
    view.adapter = HourAdapter(list,view.context,offset)
}
@BindingAdapter("set_progress")
fun setProgress(view:RecyclerView,progressEnd:MutableLiveData<Boolean>)
{
    progressEnd.postValue(true)

}
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("time","offset")
fun set_time(view: TextView,time:String,offset: Long)
{
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = time.replace(".","").toLong() * 1000
    val hours = (cal[Calendar.HOUR_OF_DAY]).toString()
//    val minutes = cal[Calendar.MINUTE]
//    val seconds = cal[Calendar.SECOND]



    var t = if (hours.length<2) "0$hours:00" else "$hours:00"
    val new_offset = (offset/60/60) - getTimeOffset()
    Log.e("current offset",new_offset.toString())
    Log.e("offset",(offset) .toString())
    Log.e("local offset",getTimeOffset() .toString())
    var currentTime :Long= 0
    if (new_offset != 0.toLong()) {
         currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            .substring(0, 2).toLong() + new_offset
    }
    else{
        currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            .substring(0, 2).toLong()
    }
    Log.e("current time",currentTime.toString())
    Log.e("time",t.substring(0,2))
    Log.e("time",t)

    t = if (currentTime == t.substring(0,2).toLong()) "now" else t

    view.setText(t)
}
fun getTimeOffset():Long{

    val calendar = Calendar.getInstance(
        TimeZone.getTimeZone("GMT"),
        Locale.getDefault()
    )
    val currentLocalTime = calendar.time
    val date: SimpleDateFormat = SimpleDateFormat("Z")
    val localTime: String = date.format(currentLocalTime)
    println(localTime)
    val l = localTime.subSequence(0,3)
    return l.toString().toLong()
}
@BindingAdapter("animation_icon")
fun set_animation_icon(view: LottieAnimationView,current: Current)
{
    get_icons(current.weather?.get(0)?.icon,view)
}
fun get_icons(idCode:String?,view:LottieAnimationView)
{
    when (idCode){
        "01d"->{ view.setAnimation(R.raw.day_broken_clouds)  }//clear sky
        "01n"->{ view.setAnimation(R.raw.weather_night_clear_sky)}
        "02d"->{ view.setAnimation( R.raw.day_broken_clouds)}//few clouds
        "02n"->{view.setAnimation( R.raw.weather_night_clear_sky)}
        "03d"->{view.setAnimation(R.raw.day_broken_clouds)}//scattered clouds
        "03n"->{view.setAnimation( R.raw.night_mist)}
        "04d"->{view.setAnimation( R.raw.day_broken_clouds)} //broken clouds
        "04n"->{view.setAnimation( R.raw.night_mist)}
        "09d"->{view.setAnimation( R.raw.day_shower_rains)}//shower rain
        "09n"->{view.setAnimation(R.raw.night_shower_rains)}
        "10d"->{view.setAnimation(R.raw.day_shower_rains)}//	rain
        "10n"->{view.setAnimation(R.raw.night_rain)}
        "11d"->{view.setAnimation( R.raw.thunderstorm)}//	thunderstorm
        "11n"->{view.setAnimation( R.raw.thunderstormn)}
        "13d"->{view.setAnimation( R.raw.day_snow)}//snow
        "13n"->{view.setAnimation( R.raw.night_snow)}
        "50d"->{view.setAnimation( R.raw.night_mist)} //mist
        "50n"->{view.setAnimation( R.raw.day_broken_clouds)}
        else->{
            Log.e("icon error","error icon")
            view.setAnimation( R.raw.day_broken_clouds)
        }
    }
}
@BindingAdapter("background_day")
fun set_background(view: ConstraintLayout, current: Current)
{
    get_background(current.weather?.get(0)?.icon,view)

}

fun get_background(icon: String?, view: ConstraintLayout) {
    if (icon?.contains("d",true)!!)
    {
        view.setBackgroundResource(R.drawable.gradient_day)
    }else{
        view.setBackgroundResource(R.drawable.gradient_night)

    }

}


