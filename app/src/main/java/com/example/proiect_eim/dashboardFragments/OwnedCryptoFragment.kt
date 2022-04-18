package com.example.proiect_eim.dashboardFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proiect_eim.R
import com.example.proiect_eim.recyclerView.OwnedCryptoAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_owned_crypto.*
import java.lang.reflect.Type

class OwnedCryptoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_owned_crypto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        owned_crypto_back_btn.setOnClickListener {
           activity?.supportFragmentManager?.popBackStack()
        }

        val gson = Gson()
        val bundle = arguments
        val json = bundle?.getString("crypto").toString()
        val type: Type = object: TypeToken<List<String>>(){}.type
        val cryptocurrencies = gson.fromJson<List<String>>(json, type)


        owned_crypto_fragment_rv?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = OwnedCryptoAdapter(cryptocurrencies)
            adapter?.notifyDataSetChanged()
        }
    }
}