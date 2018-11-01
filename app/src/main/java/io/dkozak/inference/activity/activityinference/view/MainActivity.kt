package io.dkozak.inference.activity.activityinference.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.dkozak.inference.activity.activityinference.BROADCAST_DETECTED_ACTIVITY
import io.dkozak.inference.activity.activityinference.CONFIDENCE
import io.dkozak.inference.activity.activityinference.ExceptionHandler
import io.dkozak.inference.activity.activityinference.R
import io.dkozak.inference.activity.activityinference.service.BackgroundDetectedActivitiesService


private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var txtActivity: TextView
    private lateinit var txtConfidence: TextView
    private lateinit var imgActivity: ImageView
    private lateinit var btnStartTracking: Button
    private lateinit var btnStopTracking: Button
    private lateinit var btnShowData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
        setContentView(R.layout.activity_main)

        txtActivity = findViewById(R.id.txt_activity)
        txtConfidence = findViewById(R.id.txt_confidence)
        imgActivity = findViewById(R.id.img_activity)
        btnStartTracking = findViewById(R.id.btn_start_tracking)
        btnStopTracking = findViewById(R.id.btn_stop_tracking)
        btnShowData = findViewById(R.id.btn_show_data)

        btnStartTracking.setOnClickListener { startTracking() }
        btnStopTracking.setOnClickListener { stopTracking() }
        btnShowData.setOnClickListener { goToShowData() }

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == BROADCAST_DETECTED_ACTIVITY) {
                    val type = intent.getIntExtra("type", -1)
                    val confidence = intent.getIntExtra("confidence", 0)
                    handleUserActivity(type, confidence)
                }
            }
        }
    }

    private fun goToShowData() {
        val intent = Intent(this, InferenceResultActivity::class.java)
        startActivity(intent)
    }

    private fun handleUserActivity(type: Int, confidence: Int) {
        val (label, icon) = getVisualRepresentationFor(type, this)

        Log.e(TAG, "User activity: $label, Confidence: $confidence")

        if (confidence > CONFIDENCE) {
            txtActivity.text = label
            txtConfidence.text = "Confidence: $confidence"
            imgActivity.setImageResource(icon)
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver,
            IntentFilter(BROADCAST_DETECTED_ACTIVITY)
        )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    private fun startTracking() {
        val intent = Intent(this, BackgroundDetectedActivitiesService::class.java)
        startService(intent)
    }

    private fun stopTracking() {
        val intent = Intent(this, BackgroundDetectedActivitiesService::class.java)
        stopService(intent);
    }


}
