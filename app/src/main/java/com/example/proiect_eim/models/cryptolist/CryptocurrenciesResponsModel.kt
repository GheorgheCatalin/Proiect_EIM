package com.example.proiect_eim.models.cryptolist

import com.example.proiect_eim.models.cryptolist.CryptoListDataModel
import com.google.gson.annotations.SerializedName

class CryptoListResponseModel(
    @SerializedName("data")
    var data : Array<CryptoListDataModel>,
    @SerializedName("status")
    var status : CryptoListStatusModel,
)
