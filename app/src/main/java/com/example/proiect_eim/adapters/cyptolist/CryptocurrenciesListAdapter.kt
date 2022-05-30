package com.example.proiect_eim.adapters.cyptolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_eim.R
import com.example.proiect_eim.models.cryptolist.CryptoListDataModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.cryptocurrency_item.view.*

class CryptocurrenciesListAdapter(
    private var cryptoList: Array<CryptoListDataModel>
) : RecyclerView.Adapter<CryptocurrenciesListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptocurrenciesListViewHolder {
        return CryptocurrenciesListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cryptocurrency_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }


    override fun onBindViewHolder(holder: CryptocurrenciesListViewHolder, position: Int) {
        holder.bind(cryptoList[position])

        // TODO - onClick open crypto details fragment
        //setOnClickListeners(holder, users[position])

        //TODO add string to const
        holder.itemView.add_cryptocurrency_icon.setOnClickListener {
            val cryptocurrencyName = holder.itemView.cryptocurrency_name.text.toString()
            val cryptocurrencySymbol = holder.itemView.cryptocurrency_name.text.toString()
            //Temporary
            //val addedAmount = holder.itemView.addedAmountET

            FirebaseFirestore.getInstance()
                .collection("crypto_currencies")
                .document("all")
                .update(
                    cryptocurrencyName,
                            mapOf(
                                "name" to cryptocurrencyName,
                                "symbol" to cryptocurrencySymbol,
                                "amount" to "1"
                            )
                )
        }
    }

}