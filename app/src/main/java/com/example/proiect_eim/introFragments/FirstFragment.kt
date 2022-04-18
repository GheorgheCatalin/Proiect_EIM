package com.example.proiect_eim.introFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proiect_eim.R
import com.example.proiect_eim.authentication.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.view_pager

        first_next_btn.setOnClickListener {
            viewPager?.currentItem = 1
        }

        first_skip_tv.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences("IntroDone", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.putBoolean("introDoneFlag", true)?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}