package io.dkozak.inference.activity.activityinference.view

import android.content.*
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.dkozak.inference.activity.activityinference.*
import io.dkozak.inference.activity.activityinference.service.BackgroundDetectedActivitiesService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var txtActivity: TextView
    private lateinit var txtConfidence: TextView
    private lateinit var imgActivity: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
        setContentView(R.layout.activity_main)

        txtActivity = findViewById(R.id.txt_activity)
        txtConfidence = findViewById(R.id.txt_confidence)
        imgActivity = findViewById(R.id.img_activity)
        val btnStartTracking = findViewById<Button>(R.id.btn_start_tracking)
        val btnStopTracking = findViewById<Button>(R.id.btn_stop_tracking)
        val btnShowData = findViewById<Button>(R.id.btn_show_data)
        val btnCopyToClipboard = findViewById<Button>(R.id.btn_copy_to_clipboard)

        btnStartTracking.setOnClickListener { startTracking() }
        btnStopTracking.setOnClickListener { stopTracking() }
        btnShowData.setOnClickListener { goToShowData() }
        btnCopyToClipboard.setOnClickListener { copyDataToClipboard() }

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

    private fun copyDataToClipboard() {
        GlobalScope.launch {
            val database = ActivityInferenceDatabase.getInstance(this@MainActivity)
            val dao = database.inferenceResultDao()
            val inferenceResults = dao.getAllNow()
            val serializedResults = inferenceResults.map { "${it.type},${it.confidence},${it.timestamp}" }
                .reduce { acc, unit -> "$acc\n$unit" }
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.primaryClip = ClipData.newPlainText("foo", serializedResults)
            Toast.makeText(this@MainActivity, "Data copied", Toast.LENGTH_LONG).show()
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
