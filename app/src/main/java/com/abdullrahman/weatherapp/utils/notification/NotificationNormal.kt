package com.abdullrahman.weatherapp.utils.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.abdullrahman.weatherapp.MainActivity
import com.abdullrahman.weatherapp.R
import kotlin.random.Random

class NotificationNormal(val context: Context, message:String) {
    val channelId = "channelId_normal"
    val ChannelName = "ChannelName_Normal"
    val NOTIFICATIONID = Random(100).nextInt()
    val notificationManager: NotificationManagerCompat
    val notification: Notification
    init {

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        }



        CreatenotificationChannel(context)
        notification = NotificationCompat.Builder(context,channelId)
            .setContentTitle("Weather Conditions")
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setWhen(0)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .build()
        notificationManager = NotificationManagerCompat.from(context)
}



    fun notifyBtn()
    {
        notificationManager.notify(NOTIFICATIONID,notification)

    }
    fun CreatenotificationChannel(context: Context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, ChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

    }
}