package com.example.tasker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.tasker.utils.NotificationHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.showNotification(context, "Automation Triggered!", "It's time to act.")
    }
}