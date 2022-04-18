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
import kotlinx.android.synthetic.main.fragment_third.*

class ThirdFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        third_start_btn.setOnClickListener {
            //val sharedPreferences = getSharedPreferences("IntroDone", Context.MODE_PRIVATE)
            val sharedPreferences = activity?.getSharedPreferences("IntroDone", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.putBoolean("introDoneFlag", true)?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}