package com.example.moviesearch.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectionChecker : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1 == null) {
            return
        }
        when (p1.action) {
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(p0, BATTERY_LOW, Toast.LENGTH_SHORT).show()
            Intent.ACTION_POWER_CONNECTED -> Toast.makeText(p0, POWER_CONNECTED, Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val BATTERY_LOW = "слабый заряд аккумулятора"
        private const val POWER_CONNECTED = "идет зарядка аккумулятора"
    }
}
