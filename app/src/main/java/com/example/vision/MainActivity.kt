package com.example.vision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*

private const val SIMPLE_GESTURE = "Gestures DETECTEED"
class MainActivity : AppCompatActivity() {

    private lateinit var myDetector : GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDetector = GestureDetectorCompat(this,MyGestureListener())

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        myDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private class MyGestureListener:GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            Log.d(SIMPLE_GESTURE,"Single tap confirmed")

            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Log.d(SIMPLE_GESTURE,"DOUBLE tap confirmed")
            return true
        }
    }


    /*msgBox.setOnClickListener{
        val text = "You clicked messaging!"
        val duration = Toast.LENGTH_SHORT
        Toast.makeText(this, text, duration).show()


    phoneMngr.setOnClickListener{
        val text = "You clicked phone manager!"
        val duration = Toast.LENGTH_SHORT
        Toast.makeText(this, text, duration).show()
    }


    timeDate.setOnClickListener{
        val text = "You clicked time/date!"
        val duration = Toast.LENGTH_SHORT
        Toast.makeText(this, text, duration).show()
    }


    cameraCard.setOnClickListener{
        val text = "You clicked camera!"
        val duration = Toast.LENGTH_SHORT
        Toast.makeText(this, text, duration).show()
    }*/



    }




