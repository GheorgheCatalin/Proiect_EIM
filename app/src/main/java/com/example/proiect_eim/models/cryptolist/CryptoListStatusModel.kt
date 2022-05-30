package com.example.proiect_eim.models.cryptolist

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class CryptoListStatusModel (
    @SerializedName("timestamp")
    var timestamp : String,    //Sau string   sau Timestamp   val startDateBudget = sdf.parse(budget?.startDate)  val sdf = SimpleDateFormat("dd-MM-yyyy")
    @SerializedName("error_code")
    var error_code : Int,
    @SerializedName("error_message")
    var error_message : String,
    @SerializedName("elapsed")
    var elapsed : Int,
    @SerializedName("credit_count")
    var credit_count : Int
)
