package com.example.proiect_eim.network2

import com.example.proiect_eim.constants.APIConstants.COINMARKET_API_KEY
import com.example.proiect_eim.models.cryptolist.CryptoListResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CryptoAPI {

    // "X-CMC_PRO_API_KEY: "$COINMARKET_API_KEY",
    @Headers(
        "X-CMC_PRO_API_KEY: $COINMARKET_API_KEY",
        "Accept: application/json"
    )
    @GET("latest")
    fun getCryptocurrenciesList() : Call<CryptoListResponseModel>
    // limit integer
    //integer [ 1 .. 5000 ]
    //Default: 100
    //fun getCryptocurrenciesList(@Query (limit :  )) : Call<CryptoListResponseModel>

}