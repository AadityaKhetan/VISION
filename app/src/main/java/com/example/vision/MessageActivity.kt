package com.example.vision

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.messaging.*

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messaging)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS), 111
            )
        } else
            receiveMsg()

        sendMsg.setOnClickListener {
            val sms: SmsManager = SmsManager.getDefault()
            sms.sendTextMessage(
                editTextPhone.text.toString(),
                "ME",
                editTextTextMultiLine.text.toString(),
                null,
                null
            )
        }
    }

    private fun receiveMsg() {
        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (sms: SmsMessage in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                        editTextPhone.setText(sms.originatingAddress)
                        editTextTextMultiLine.setText(sms.displayMessageBody)
                        Toast.makeText(applicationContext, "Msg received", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}