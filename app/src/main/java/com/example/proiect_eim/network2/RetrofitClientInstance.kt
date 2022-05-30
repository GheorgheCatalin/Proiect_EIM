package com.example.proiect_eim.network2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    companion object {
        //private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/"
    }

    fun getRetrofitInstance(): Retrofit?{
        if (retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        return retrofit
    }

}