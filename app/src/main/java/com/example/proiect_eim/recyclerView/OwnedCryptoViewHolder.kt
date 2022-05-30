package com.example.proiect_eim.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_eim.models.DBCryptoModel
import kotlinx.android.synthetic.main.owned_crypto_item.view.*

class OwnedCryptoViewHolder (
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: DBCryptoModel){
        itemView.owned_crypto_name.text = item.cryptocurrencyName
        val symbol = "(" + item.cryptocurrencySymbol + ")"
        itemView.owned_crypto_symbol.text = symbol
        itemView.owned_crypto_amount.text = item.amount

        //getCryptoValue()
    }
}