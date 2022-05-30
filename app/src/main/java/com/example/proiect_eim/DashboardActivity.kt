package com.example.proiect_eim

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.proiect_eim.dashboardFragments.AccountFragment
import com.example.proiect_eim.dashboardFragments.CurrenciesFragment
import com.example.proiect_eim.dashboardFragments.HomeFragment
import com.example.proiect_eim.dashboardFragments.SavedFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardActivity : AppCompatActivity() {
    lateinit var receiver: CustomBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        receiver = CustomBroadcastReceiver()

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }

//        val notificationManager: NotificationManager =
//            ContextCompat.getSystemService(baseContext, NotificationManager::class.java) as NotificationManager

        val homeFragment = HomeFragment()
        val postsFragment = CurrenciesFragment()
        val savedFragment = SavedFragment()
        val accountFragment = AccountFragment()

        setCurrentFragment(homeFragment)

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.page_home -> setCurrentFragment(homeFragment)
                R.id.page_currencies -> setCurrentFragment(postsFragment)
                R.id.page_saved -> setCurrentFragment(savedFragment)
                R.id.page_settings -> setCurrentFragment(accountFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragment_container, fragment)
            commit()
        }
}