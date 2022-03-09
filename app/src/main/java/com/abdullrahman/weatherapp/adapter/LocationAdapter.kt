package com.abdullrahman.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.weatherapp.R
import com.abdullrahman.weatherapp.databinding.ItemPlaceBinding
import com.abdullrahman.weatherapp.model.DailyItem
import com.abdullrahman.weatherapp.room.RoomDataBaseModel

class LocationAdapter(val list:ArrayList<RoomDataBaseModel>?,val context: Context):
    RecyclerView.Adapter<LocationAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_place,
            parent,
            false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemPlace.api = list?.get(position)
        holder.itemPlace.address.isSelected = true
    }

    override fun getItemCount() = if(list!=null) list.size else 0
    fun removeItem(position: Int):String
    {
        val id = list!!.get(position).id
        list?.removeAt(position)
        notifyItemRemoved(position)
        return id
    }
    inner class VH(val itemPlace:ItemPlaceBinding):RecyclerView.ViewHolder(itemPlace.root)

}