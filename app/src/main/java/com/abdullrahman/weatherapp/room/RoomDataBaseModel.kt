package com.abdullrahman.weatherapp.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdullrahman.weatherapp.model.Current
import com.abdullrahman.weatherapp.model.DailyItem
import com.abdullrahman.weatherapp.model.HourlyItem
import com.abdullrahman.weatherapp.room.converter.DailyItemTypeConverter
import com.abdullrahman.weatherapp.room.converter.HourlyItemTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName ="Place")
@JvmSuppressWildcards
@TypeConverters(
	DailyItemTypeConverter::class,
	HourlyItemTypeConverter::class
)

data class RoomDataBaseModel(
	@field:SerializedName("id")
	@PrimaryKey
	val id:String ,

	@field:SerializedName("current")
	@Embedded(prefix = "current_")
	val current: Current? = null,
	@field:SerializedName("address")
	val address: String? = null,

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
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString().toString(),
		TODO("current"),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Long::class.java.classLoader) as? Long,
		TODO("daily"),
		parcel.readValue(Double::class.java.classLoader) as? Double,
		TODO("hourly"),
		parcel.readValue(Double::class.java.classLoader) as? Double
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
		parcel.writeString(address)
		parcel.writeString(timezone)
		parcel.writeValue(timezoneOffset)
		parcel.writeValue(lon)
		parcel.writeValue(lat)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<RoomDataBaseModel> {
		override fun createFromParcel(parcel: Parcel): RoomDataBaseModel {
			return RoomDataBaseModel(parcel)
		}

		override fun newArray(size: Int): Array<RoomDataBaseModel?> {
			return arrayOfNulls(size)
		}
	}

}






