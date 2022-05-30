package com.example.proiect_eim.dashboardFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proiect_eim.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import kotlinx.android.synthetic.main.fragment_saved.*
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SavedFragment : Fragment() {
    var snapsnotListener : ListenerRegistration? = null
    //var storedAmounts: MutableSet<MutableMap.MutableEntry<String, Any>>? = null
    lateinit var amountEntries: ArrayList<Entry>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStoredAmounts()
    }

    override fun onStop() {
        super.onStop()
        snapsnotListener?.remove()
    }

    private fun getStoredAmounts() {
        snapsnotListener = FirebaseFirestore.getInstance().collection("crypto_total_amount").document("all")
            .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
                if (e != null) {
                    Toast.makeText(context, "Error: " + e.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                if (snapshot != null && snapshot.exists()) {

                    //val storedAmounts = convertResponseToAmountMap(snapshot.data?.entries)
                    val storedAmounts = snapshot.data?.entries

                    showLineChart(storedAmounts)

                    //progress_bar.visibility = View.GONE

                } else {
                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun showLineChart(storedAmounts: MutableSet<MutableMap.MutableEntry<String, Any>>?) {

        line_chart.setTouchEnabled(true)
        line_chart.setPinchZoom(true)
//
        val xAxis =  line_chart.xAxis
        val leftAxis = line_chart.axisLeft

//        val chartValues: ArrayList<Map.Entry<*, *>> = ArrayList()
//        chartValues.add(entry(1, 50))
//        chartValues.add(MutableMap.MutableEntry<Any?, Any?>(2, 100))

        //val set = LineDataSet(storedAmounts, "Sample Data")


        //        hide grid lines
        line_chart.axisLeft.setDrawGridLines(false)
//        val xAxis: XAxis = line_chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        line_chart.axisRight.isEnabled = false

        //remove legend
        line_chart.legend.isEnabled = false

        //remove description label
        line_chart.description.isEnabled = false

        //add animation
        line_chart.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        //xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        //xAxis.labelRotationAngle = +90f

        setDataToLineChart(storedAmounts)
    }

    private fun setDataToLineChart(storedAmounts: MutableSet<MutableMap.MutableEntry<String, Any>>?) {
        val entries: ArrayList<Entry> = ArrayList()

        //scoreList = getScoreList()

        //you can replace this data object with  your custom object

        if (storedAmounts != null) {
            var i = 0;
            for (storedAmount in storedAmounts) {
                val timestamp = storedAmount.key.toInt() % 10000f
                val amount = (storedAmount.value as String).toFloat()

                entries.add(Entry(i.toFloat(), amount))
                i++
            }
        }

        amountEntries = entries

        //val xAxis: XAxis = line_chart.xAxis
        //xAxis.valueFormatter = MyAxisFormatter()

        val lineDataSet = LineDataSet(entries, "")

        val data = LineData(lineDataSet)
        line_chart.data = data

        line_chart.invalidate()
    }

//    inner class MyAxisFormatter : IndexAxisValueFormatter() {
//
//        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
//            val index = value.toInt()
//            return if (index < amountEntries?.size) {
//                amountEntries[index].x.toString()
//            } else {
//                ""
//            }
//        }
//    }

//    inner class MyAxisFormatter : IndexAxisValueFormatter() {
//        override fun getFormattedValue(value: Float): String {
//            val emissionsMilliSince1970Time = value.toLong() * 1000
//
//            val timeMilliseconds = Date(emissionsMilliSince1970Time)
//            val dateTimeFormat: DateFormat =
//                DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
//
//            return super.getFormattedValue(value)
//        }
//
//    }
}