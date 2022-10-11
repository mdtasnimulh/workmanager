package com.example.myworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.myworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            binding.oneTimeWork.setOnClickListener{
                myOneTimeWork()
            }

            binding.periodicWork.setOnClickListener {
                myPeriodicWork()
            }
        }
    }

    fun myOneTimeWork(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(false)
            .setRequiresStorageNotLow(false)
            .build()
        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueue(myWorkRequest)
    }

    fun myPeriodicWork(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(false)
            .setRequiresStorageNotLow(false)
            .build()
        val myWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("my_id", ExistingPeriodicWorkPolicy.KEEP, myWorkRequest)
    }
}