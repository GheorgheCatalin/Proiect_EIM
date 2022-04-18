package com.example.proiect_eim.recyclerView


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_eim.R

class OwnedCryptoAdapter(
    private var ownedCrypto: List<String>
) : RecyclerView.Adapter<OwnedCryptoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnedCryptoViewHolder {
        return OwnedCryptoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.owned_crypto_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return ownedCrypto.size
    }

    override fun onBindViewHolder(holder: OwnedCryptoViewHolder, position: Int) {
        holder.bind(ownedCrypto[position])
    }

}