package io.dkozak.inference.activity.activityinference

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.google.android.gms.location.ActivityRecognitionClient


private val TAG = BackgroundDetectedActivitiesService::class.java.simpleName

class BackgroundDetectedActivitiesService : Service() {

    inner class LocalBinder : Binder() {
        fun getServerInstance() = this@BackgroundDetectedActivitiesService
    }

    private val binder = LocalBinder()

    private lateinit var activityRecognitionClient: ActivityRecognitionClient
    private lateinit var intentService: Intent
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate() {
        super.onCreate()
        activityRecognitionClient = ActivityRecognitionClient(this)
        intentService = Intent(this, DetectedActivitiesIntentService::class.java)
        pendingIntent = PendingIntent.getService(this, 1, intentService, PendingIntent.FLAG_UPDATE_CURRENT)
        requestActivityUpdatesButtonHandler()
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    private fun requestActivityUpdatesButtonHandler() {
        val task = activityRecognitionClient.requestActivityUpdates(
            DETECTION_INTERVAL_IN_MILLISECONDS,
            pendingIntent
        )

        task.addOnSuccessListener {
            Toast.makeText(applicationContext, "Successfully requested activity updates", Toast.LENGTH_SHORT)
                .show()
        }

        task.addOnFailureListener {
            Toast.makeText(applicationContext, "Requesting activity updates failed to start", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeActivityUpdatesButtonHandler()

    }

    fun removeActivityUpdatesButtonHandler() {
        val task = activityRecognitionClient.removeActivityUpdates(pendingIntent)
        task.addOnSuccessListener {
            Toast.makeText(applicationContext, "Removed activity updates successfully!", Toast.LENGTH_SHORT)
                .show()
        }

        task.addOnFailureListener {
            Toast.makeText(applicationContext, "Failed to remove activity updates!", Toast.LENGTH_SHORT).show()
        }
    }
}

