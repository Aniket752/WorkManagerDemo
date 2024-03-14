package com.mavericklabs.workmanagerdemo

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources.Theme
import android.os.Build
import android.renderscript.RenderScript.Priority
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

@RequiresApi(Build.VERSION_CODES.O)
class DemoWorker(private val context : Context, private val workerParameters: WorkerParameters) : Worker(context,workerParameters) {
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var notification: NotificationCompat.Builder
    private val channel = NotificationChannel("01","Worker",NotificationManager.IMPORTANCE_HIGH)

    override fun doWork(): Result {
        Log.d("DemoWorker","Work Started")
        sendNotification()
        val sleep = workerParameters.inputData.getLong("time",0)
        if (sleep == 0L)
            return  Result.retry()
        for (i in 1..12098)
            println(i)
        notificationManager.cancel(1)
        return Result.success(Data.Builder().putString("comp","Hay").build())
    }

    private fun sendNotification(){
        Log.d("DemoWorker","Notification Started")

        notification = NotificationCompat.Builder(applicationContext,"Worker")
        notification.setContentText("Worker is running")
        notification.setContentInfo("Background Task")
//        notification.setPriority(Notification.PRIORITY_MAX)
        notification.setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_bg)

        notificationManager = NotificationManagerCompat.from(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(channel.id)
            notification.build()
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(1,notification.build())
        }else
             notificationManager.notify(1,notification.build())

    }
}