package com.example.dispositivos.ui.utilities

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.example.dispositivos.R
import com.example.dispositivos.ui.activities.CameraActivity
import com.example.dispositivos.ui.activities.EmptyActivity
import com.example.dispositivos.ui.activities.NotificationActivity

class BroadcasterNotifications: BroadcastReceiver(){

    val CHANNEL: String ="Notificaciones"

    override fun onReceive(context: Context, intent: Intent?) {

        val myIntent = Intent(context,CameraActivity::class.java)

        val myPendingIntent= PendingIntent.getBroadcast(context,
        0,
        myIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val noti = NotificationCompat.Builder(context, CHANNEL)
        noti.setContentTitle("Primera notificacion")
        noti.setContentText("Tienes una notificacion")
        noti.setSmallIcon(R.drawable.face_icon)
        //noti.setLargeIcon(R.drawable. debe ir a un bitmap
        noti.setPriority(NotificationCompat.PRIORITY_MAX)
        noti.setStyle(NotificationCompat.BigTextStyle().bigText("Esta es una notificacion para recordar que estamos trabajando en android"))
        noti.setContentIntent(myPendingIntent)
        val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(
            1,
            noti.build()

        )
    }
}