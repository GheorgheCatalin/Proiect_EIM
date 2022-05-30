package com.example.proiect_eim

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.Notification
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService





class CustomBroadcastReceiver : BroadcastReceiver(){
    companion object {
        const val NOTIFICATION_CHANNEL_ID = "broadcast_notification"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return

        val contentTitle = "Airplane Mode Changed"
        var contentText = ""
        var contentInfo = ""

        if (isAirplaneModeEnabled) {
            //Toast.makeText(context, "Action: " + intent.action, Toast.LENGTH_SHORT).show()
            contentText = "Finance App is unable to retrieve data in airplane mode"
            contentInfo = "Finance App is unable to retrieve data in airplane mode"
        } else {
            //Toast.makeText(context, "Action: " + intent.action, Toast.LENGTH_SHORT).show()
            contentText = "Finance App is back online"
            contentInfo = "Finance App is back online"
        }

        notificationDialog(context, contentTitle, contentText, contentInfo)
    }


    private fun notificationDialog(
        context: Context?,
        contentTitle: String,
        contentText: String,
        contentInfo: String
    ) {
        val notificationManager: NotificationManager =
            context?.let { getSystemService(it, NotificationManager::class.java) } as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Airplane Mode Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.description = "Airplane Mode Notification"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder: NotificationCompat.Builder? =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)

        notificationBuilder?.setAutoCancel(true)
            ?.setDefaults(Notification.DEFAULT_ALL)
            ?.setWhen(System.currentTimeMillis())
            ?.setSmallIcon(R.drawable.ic_baseline_airplanemode_active_24)
            //?.setTicker("...")
            ?.setPriority(NotificationCompat.PRIORITY_MAX)
            ?.setContentTitle(contentTitle)
            ?.setContentText(contentText)
            ?.setContentInfo(contentInfo)

        notificationManager.notify(1, notificationBuilder?.build())
    }
}