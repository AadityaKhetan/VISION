package com.example.vision

import android.content.Intent

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech

import android.view.View
import android.widget.Button

import android.widget.Toast
import androidx.annotation.RequiresApi

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener,
    View.OnLongClickListener,
    TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        //val text = "Welcome to Vision.Single tap for details and long press to open an activity."

        //speak(text)

        msgBox.setOnClickListener(this)
        phoneMngr.setOnClickListener(this)
        timeDate.setOnClickListener(this)
        cameraCard.setOnClickListener(this)

        msgBox.setOnLongClickListener(this)
        phoneMngr.setOnLongClickListener(this)
        timeDate.setOnLongClickListener(this)
        cameraCard.setOnLongClickListener(this)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(view: View) {
        val text = when (view.id) {
            R.id.msgBox -> "You clicked messaging!"
            R.id.phoneMngr -> "You clicked phone manager!"
            R.id.timeDate -> "You clicked Time/Date and Battery status!"
            R.id.cameraCard -> "You clicked phone camera!"
            else -> throw IllegalArgumentException("Undefined Clicked")

        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        speak(text)

    }

    override fun onLongClick(view: View): Boolean {
        val intent = when (view.id) {
            R.id.msgBox -> Intent(this, MessageActivity::class.java)
            R.id.phoneMngr -> Intent(this, PhoneActivity::class.java)
            R.id.timeDate -> Intent(this, TimeDateActivity::class.java)
            R.id.cameraCard -> Intent(this, CameraActivity::class.java)
            //R.id.cameraCard -> Intent(this, Camera::class.java)
            else -> throw IllegalArgumentException("Undefined Clicked")
        }
        startActivity(intent)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speak(text: String) {

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts!!.language = Locale.US
            tts?.speak(
                "Welcome to Vision.Single tap for details and long press to open an activity.",
                TextToSpeech.QUEUE_FLUSH, null, null
            )
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
}



