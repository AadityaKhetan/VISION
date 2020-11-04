package com.example.vision

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.datetime.*
import java.text.SimpleDateFormat
import java.util.*

val TAG = "SPEECH"

class TimeDateActivity : AppCompatActivity(), View.OnClickListener, TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.datetime)
        tts = TextToSpeech(this, this)
        batteryCard.setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(view: View) {

        if (view.id == R.id.batteryCard) {

            val currDate = findViewById<TextView>(R.id.currDateTime)
            val date = getCurrentDateTime()
            val dateInString = date.toString("E,dd MMMM yyyy HH:mm:ss")
            currDate.text = dateInString
            tts?.speak(dateInString, TextToSpeech.QUEUE_FLUSH, null, null)
            Thread.sleep(6000)
            val batteryStatus: Intent? =
                IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
                    this.registerReceiver(null, ifilter)

                }
            val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            var isCharging = when (status) {
                BatteryManager.BATTERY_STATUS_CHARGING -> "Phone is charging"
                else -> "Phone is not charging"
            }

            val txtView = findViewById<TextView>(R.id.batteryStatus)

            txtView.text = isCharging

            val batstate = "Your $isCharging"


            val batteryPct: Float? = batteryStatus?.let { intent ->
                val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                level * 100 / scale.toFloat()
            }

            val txtview = findViewById<TextView>(R.id.batteryPer)

            txtview.text = batteryPct.toString()

            val bst = "Your battery level is " + batteryPct.toString() + "percent and $batstate"
            tts?.speak(bst, TextToSpeech.QUEUE_FLUSH, null, null)
        }


    }


    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onInit(p0: Int) {
        Log.d(TAG, "Initializing TTS")
        if (p0 == TextToSpeech.SUCCESS) {
            Log.d(TAG, "SUCCESS")
            tts!!.language = Locale.US
            tts?.speak(
                "Time,date and battery status opened.",
                TextToSpeech.QUEUE_FLUSH, null, null
            )
        }
    }

    private fun Date.toString(format: String): String {
        val formatter = SimpleDateFormat(format)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

}


