package com.example.proiect_eim.models.cryptolist

import com.google.gson.annotations.SerializedName

class CryptoListDataModel (
        @SerializedName("id")
        var id : Int,
        @SerializedName("name")
        var name : String,
        @SerializedName("symbol")
        var symbol : String,
        @SerializedName("slug")
        var slug : String,  //he web URL friendly shorthand version of this cryptocurrency name.
        @SerializedName("cmc_rank")
        var cmc_rank : Int,
        @SerializedName("num_market_pairs")
        var num_market_pairs : Int,
        @SerializedName("circulating_supply")
        var circulating_supply : String,
        @SerializedName("total_supply")
        var total_supply : String,
        @SerializedName("quote")
        var quote : CryptoListQuotes
)

class CryptoListQuotes (
        @SerializedName("USD")
        var USD : CryptoListQuoteModel,
        @SerializedName("BTC")
        var BTC : CryptoListQuoteModel
)


class CryptoListQuoteModel (
        @SerializedName("price")
        var price : Double,
        @SerializedName("volume_24h")
        var volume_24h : Double,
        @SerializedName("volume_change_24h")
        var volume_change_24h : Double,
        @SerializedName("volume_24h_reported")
        var volume_24h_reported : Double,
        @SerializedName("market_cap")
        var market_cap : Double,
        @SerializedName("market_cap_dominance")
        var market_cap_dominance : Double,
        @SerializedName("fully_diluted_market_cap")
        var fully_diluted_market_cap : Double,
        @SerializedName("last_updated")
        var last_updated : String,
        @SerializedName("percent_change_1h")
        var percent_change_1h : Double,
        @SerializedName("percent_change_24h")
        var percent_change_24h : Double,
        @SerializedName("percent_change_7d")
        var percent_change_7d : Double
)
