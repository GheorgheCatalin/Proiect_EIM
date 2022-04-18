package com.example.proiect_eim.dashboardFragments

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.proiect_eim.introFragments.FirstFragment

class DashboardVPAdapter (
    activity: AppCompatActivity,
    private val itemsCount: Int
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1 -> CurrenciesFragment()
            2 -> SavedFragment()
            3 -> AccountFragment()
            else -> FirstFragment()
        }
    }

}