package com.example.proiect_eim.adapters.cyptolist

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_eim.models.cryptolist.CryptoListDataModel
import kotlinx.android.synthetic.main.cryptocurrency_item.view.*
import kotlin.math.roundToInt

class CryptocurrenciesListViewHolder  (
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: CryptoListDataModel) {
        itemView.cryptocurrency_name.text = item.name
        val symbol = "(" + item.symbol + ")"
        itemView.cryptocurrency_symbol.text = symbol
        val price = item.quote.USD.price.toString() + "\$"
        itemView.cryptocurrency_price_value.text = price

        //TODO change name
        setPriceChangesPercentages( item.quote.USD.percent_change_24h, itemView.cryptocurrency_24h_value)
        setPriceChangesPercentages( item.quote.USD.percent_change_7d, itemView.cryptocurrency_7d_value)
    }

    private fun setPriceChangesPercentages(priceChangePercent: Double, percentTextView: TextView) {
        //TODO get color from resources
        if (priceChangePercent >= 0)
            percentTextView.setTextColor(Color.parseColor("#00FF00"))
        else
            percentTextView.setTextColor(Color.parseColor("#FF0000"))

        val changePercentage = ((priceChangePercent * 100.0).roundToInt() / 100.0).toString() + " %"
        percentTextView.text = changePercentage
    }
}