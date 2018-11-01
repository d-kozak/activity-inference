package io.dkozak.inference.activity.activityinference.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.dkozak.inference.activity.activityinference.ActivityInferenceDatabase
import io.dkozak.inference.activity.activityinference.BROADCAST_DETECTED_ACTIVITY
import io.dkozak.inference.activity.activityinference.ExceptionHandler
import io.dkozak.inference.activity.activityinference.dao.InferenceResultDao
import io.dkozak.inference.activity.activityinference.entity.InferenceResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private val TAG = InferenceResultPersistenseService::class.java.simpleName

class InferenceResultPersistenseService : Service() {

    inner class LocalBinder : Binder() {
        fun getServerInstance() = this@InferenceResultPersistenseService
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == BROADCAST_DETECTED_ACTIVITY) {
                val type = intent.getIntExtra("type", -1)
                val confidence = intent.getIntExtra("confidence", 0)
                persistActivity(type, confidence)
            }
        }
    }

    private lateinit var inferenceResultDao: InferenceResultDao


    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
        val database = ActivityInferenceDatabase.getInstance(this)
        inferenceResultDao = database.inferenceResultDao()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver, IntentFilter(
                BROADCAST_DETECTED_ACTIVITY
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    override fun onBind(intent: Intent): IBinder = LocalBinder()

    private fun persistActivity(type: Int, confidence: Int) {
        val inferenceResult = InferenceResult().apply {
            this.type = type
            this.confidence = confidence
        }
        Log.d(TAG, "Saving inference result: $inferenceResult")
        Toast.makeText(this, "Saving inference result: $inferenceResult", Toast.LENGTH_LONG).show()
        GlobalScope.launch {
            inferenceResultDao.insert(inferenceResult)
        }
    }
}

