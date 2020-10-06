package com.example.vision

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BatteryStatusReciever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val action = p1?.action
        val text = when (action) {
            Intent.ACTION_POWER_CONNECTED -> "Battery Status is charging"
            Intent.ACTION_POWER_DISCONNECTED -> "Battery Status is not charging"
            else -> "Cannot identify"
        }
    }
}