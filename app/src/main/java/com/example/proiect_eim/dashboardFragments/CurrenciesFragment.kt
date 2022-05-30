package com.example.proiect_eim.dashboardFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proiect_eim.R
import com.example.proiect_eim.adapters.cyptolist.CryptocurrenciesListAdapter
import com.example.proiect_eim.models.cryptolist.CryptoListResponseModel
import com.example.proiect_eim.network2.CryptoAPI
import com.example.proiect_eim.network2.RetrofitClientInstance
import com.example.proiect_eim.objects.UserItem
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_currencies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrenciesFragment : Fragment() {
    lateinit var user : UserItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getCryptocurrenciesList()
    }

    private fun getCryptocurrenciesList() {
        val service = RetrofitClientInstance().getRetrofitInstance()
            ?.create<CryptoAPI>(CryptoAPI::class.java)

        val request = service?.getCryptocurrenciesList()

        request?.enqueue(object : Callback<CryptoListResponseModel> {
            override fun onFailure(call: Call<CryptoListResponseModel>, t: Throwable) {
                Log.i("getCryptoListCall",
                    "Failed getting crypto list: " + t.message.toString())

               //TODO show error message : Smth went wrong. Please try again
            }

            override fun onResponse(
                call: Call<CryptoListResponseModel>,
                response: Response<CryptoListResponseModel>
            ) {
                handleCryptoListResponse(response)
            }

        })

    }

    private fun handleCryptoListResponse(response: Response<CryptoListResponseModel>) {
        Log.i("getCryptoListCall", response.code().toString())
        if(response.isSuccessful) {
            if (response.body() == null) {
                Toast.makeText(activity, "No cryptocurriencies have been received",
                    Toast.LENGTH_SHORT).show()
            } else {
                onCryptoListSuccessfulResponse(response)

            }
        } else {
            Toast.makeText(activity, "Getting cryptocurriencies list has failed",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCryptoListSuccessfulResponse(response: Response<CryptoListResponseModel>) {
        //TODO Loading gone

        crypto_list_rv?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = response.body()?.data?.let { CryptocurrenciesListAdapter(it) }
            adapter?.notifyDataSetChanged()
        }
    }

}