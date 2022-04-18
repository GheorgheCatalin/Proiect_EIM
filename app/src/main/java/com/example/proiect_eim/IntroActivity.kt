package com.example.proiect_eim

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proiect_eim.authentication.LoginActivity
import com.example.proiect_eim.introFragments.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class IntroActivity : AppCompatActivity() {
    var introDoneFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("IntroDone", Context.MODE_PRIVATE)

        introDoneFlag = sharedPreferences.getBoolean("introDoneFlag", false)

        if (!introDoneFlag) {
            val adapter = ViewPagerAdapter(this, 3)
            view_pager.adapter = adapter

            TabLayoutMediator(dots_tab, view_pager) { _, _ -> }.attach()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
