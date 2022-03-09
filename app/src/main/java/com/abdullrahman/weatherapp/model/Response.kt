package com.abdullrahman.weatherapp.model

import androidx.room.TypeConverters
import com.abdullrahman.weatherapp.room.converter.DailyItemTypeConverter
import com.abdullrahman.weatherapp.room.converter.HourlyItemTypeConverter
import com.abdullrahman.weatherapp.room.converter.WeatherItemTypeConverter
import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("timezone_offset")
	val timezoneOffset: Long? = null,

	@field:SerializedName("daily")
	val daily: List<DailyItem?>? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("hourly")
	val hourly: List<HourlyItem?>? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class FeelsLike(

	@field:SerializedName("eve")
	val eve: Double? = null,

	@field:SerializedName("night")
	val night: Double? = null,

	@field:SerializedName("day")
	val day: Double? = null,

	@field:SerializedName("morn")
	val morn: Double? = null
)

data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("main")
	val main: String? = null,

	@field:SerializedName("id")
	val id: Double? = null
)
@TypeConverters(HourlyItemTypeConverter::class)
data class HourlyItem(

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Double? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("clouds")
	val clouds: Double? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Double? = null,

	@field:SerializedName("wind_gust")
	val windGust: Double? = null,

	@field:SerializedName("dt")
	val dt: Long? = null,

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Double? = null,

	@field:SerializedName("dew_poDouble")
	val dewPoDouble: Double? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)

data class Temp(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null,

	@field:SerializedName("eve")
	val eve: Double? = null,

	@field:SerializedName("night")
	val night: Double? = null,

	@field:SerializedName("day")
	val day: Double? = null,

	@field:SerializedName("morn")
	val morn: Double? = null
)
@TypeConverters(WeatherItemTypeConverter::class)

data class Current(

	@field:SerializedName("sunrise")
	val sunrise: Double? = null,

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Double? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("clouds")
	val clouds: Double? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Double? = null,

	@field:SerializedName("wind_gust")
	val windGust: Double? = null,

	@field:SerializedName("dt")
	val dt: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Double? = null,

	@field:SerializedName("dew_poDouble")
	val dewPoDouble: Double? = null,

	@field:SerializedName("sunset")
	val sunset: Double? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)
@TypeConverters(DailyItemTypeConverter::class)
data class DailyItem(

	@field:SerializedName("moonset")
	val moonset: Double? = null,

	@field:SerializedName("sunrise")
	val sunrise: Double? = null,

	@field:SerializedName("temp")
	val temp: Temp? = null,

	@field:SerializedName("moon_phase")
	val moonPhase: Double? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("moonrise")
	val moonrise: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("clouds")
	val clouds: Double? = null,

	@field:SerializedName("feels_like")
	val feelsLike: FeelsLike? = null,

	@field:SerializedName("wind_gust")
	val windGust: Double? = null,

	@field:SerializedName("dt")
	val dt: Long? = null,

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Double? = null,

	@field:SerializedName("dew_poDouble")
	val dewPoDouble: Double? = null,

	@field:SerializedName("sunset")
	val sunset: Double? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)
