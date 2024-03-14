
package com.mavericklabs.workmanagerdemo

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.impl.WorkDatabasePathHelper
import java.time.Duration
import java.util.UUID

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button = findViewById(R.id.button)

        button.setOnClickListener {
            val manager = WorkManager.getInstance(this)
            val workRequest = OneTimeWorkRequest.Builder(DemoWorker::class.java).setInputData(
                Data.Builder().putLong("time",10000023).build()
            )

            val secondWorkRequest = PeriodicWorkRequest.Builder(SecondWorker::class.java, Duration.ofSeconds(1200)).build()

            workRequest.build()
            //Start Multiple work request
//            val state = manager.beginWith(listOf(workRequest.build())).enqueue()
            //Start single work request
//            manager.enqueue(workRequest.build())
            //Start chain work requests
//            val state = manager.beginWith(workRequest.build()).then(secondWorkRequest).enqueue()
            val state = manager.enqueue(secondWorkRequest)
            state.state.observe(this){
                println("Work " + it)
            }


        }


    }
}