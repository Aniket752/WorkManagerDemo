package com.mavericklabs.workmanagerdemo

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SecondWorker(val context: Context,val workerParameters: WorkerParameters) : Worker(context,workerParameters) {
    override fun doWork(): Result {
        println("Second work started")
        return Result.success()
    }
}