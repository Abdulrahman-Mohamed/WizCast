package com.abdullrahman.weatherapp.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val list:ArrayList<Fragment>,
fm:FragmentManager,
lifeCycle:Lifecycle):FragmentStateAdapter(fm,lifeCycle) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int) = list[position]

}