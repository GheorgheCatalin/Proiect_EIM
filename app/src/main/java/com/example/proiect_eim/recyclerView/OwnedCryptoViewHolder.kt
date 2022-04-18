package com.example.proiect_eim.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.owned_crypto_item.view.*

class OwnedCryptoViewHolder (
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: String){
        itemView.owned_crypto_name.text = item
    }
}