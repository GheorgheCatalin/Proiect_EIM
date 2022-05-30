package com.example.proiect_eim.dashboardFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proiect_eim.R
import com.example.proiect_eim.authentication.LoginActivity
import com.example.proiect_eim.models.DBCryptoModel
import com.example.proiect_eim.objects.UserItem
import com.example.proiect_eim.recyclerView.OwnedCryptoAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    lateinit var user : UserItem
    var snapsnotListener : ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = UserItem("","","","","")

        getUser()
    }

    override fun onStop() {
        super.onStop()
        snapsnotListener?.remove()
    }

//    override fun onPause() {
//        super.onPause()
//        snapsnotListener?.remove()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        snapsnotListener?.remove()
//    }

    private fun getUser() {
        // At first get user's data from DB, then set up UI. It it fails, user should login again
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            val email = currentUser.email.toString()

            val docRef = FirebaseFirestore.getInstance()
                .collection("users")
                .document(email)

            docRef
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("userDoc", "DocumentSnapshot data: + ${document.data}")
                        convertDocumentToUser(document.data)
                        setUpUI()
                    } else {
                        Log.d("userDoc", "No such document")
                        logout()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("userDoc", "get failed with ", exception)
                    logout()
                }
        }
    }

    private fun setUpUI(){
        getUserName()

        getCryptoList()

        your_amount_store_tv.setOnClickListener {
            storeAmountToDataBase()
        }
    }

    private fun storeAmountToDataBase() {
        var currentAmount = your_amount_tv.text.toString()
        val timestamp = (System.currentTimeMillis()/1000).toString()
        currentAmount = currentAmount.replace(" ","")
        currentAmount = currentAmount.replace("\$","")

        FirebaseFirestore.getInstance()
            .collection("crypto_total_amount")
            .document("all")
            .update(
                mapOf(timestamp to currentAmount)
            )
    }

    private fun getCryptoList() {
        snapsnotListener = FirebaseFirestore.getInstance().collection("crypto_currencies").document("all")
            .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
                if (e != null) {
                    Toast.makeText(context, "Error: " + e.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    //logout()
                }
                if (snapshot != null && snapshot.exists()) {
//                    val cryptocurrenciesList = snapshot.data?.values?.map {
//                        it.toString()
//                    }
                    val cryptocurrenciesList = convertResponseToCryptoList(snapshot.data?.values)
                    //val cryptocurrenciesList = convertResponseToCryptoList(snapshot.data?.entries)
//                    val cryptocurrenciesList = snapshot.data?.keys?.let {
//                        convertResponseToCryptoList(
//                            it
//                        )
//                    }

                    owned_crypto_rv?.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = cryptocurrenciesList?.let { OwnedCryptoAdapter(it) }
                        adapter?.notifyDataSetChanged()
                    }

                    owned_crypto_progress_bar.visibility = View.GONE

                    owned_crypto_see_more.setOnClickListener{
                        cryptocurrenciesList?.let {
                            handleSeeMore(it)
                        }
                    }

                } else {
                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun convertResponseToCryptoList(entries: MutableCollection<Any>?): List<DBCryptoModel> {
        val cryptocurrenciesList = mutableListOf<DBCryptoModel>()

        if (entries != null) {
            for (entry in entries){
                if (entry is HashMap<*, *>) {
                    cryptocurrenciesList.add(
                        DBCryptoModel(
                            entry["name"] as String?,
                            entry["amount"] as String?,
                            entry["symbol"] as String?
                        )
                    )
                }
            }
        }

        return cryptocurrenciesList
    }

    private fun handleSeeMore(news: List<DBCryptoModel>) {
        val bundle = Bundle()
        val gson = Gson()
        val json = gson.toJson(news)

        bundle.putString("crypto", json)
        val secondFragment = OwnedCryptoFragment()
        secondFragment.arguments = bundle


        activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(R.id.fragment_container, secondFragment)
            addToBackStack("home fragment")
            commit()
        }
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }

    private fun convertDocumentToUser(data: Map<String, Any>?) {
        val email = data?.get("email").toString()
        val firstName = data?.get("firstName").toString()
        val lastName = data?.get("lastName").toString()
        val country = data?.get("country").toString()
        val dateOfBirth = data?.get("dateOfBirth").toString()

        user = UserItem(email, firstName, lastName, country, dateOfBirth)
    }

    private fun getUserName(){
        val helloText = "Hello ${user.firstName}"
        hello_user_tv.text = helloText
    }

}

