package com.example.proiect_eim.dashboardFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proiect_eim.R
import com.example.proiect_eim.authentication.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            goToLoginActivity(it.context)
        }
    }

    private fun goToLoginActivity(context: Context) {
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }
}