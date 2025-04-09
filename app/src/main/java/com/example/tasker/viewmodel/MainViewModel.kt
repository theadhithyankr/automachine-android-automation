package com.example.tasker.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasker.data.Profile
import com.example.tasker.data.ProfileDatabase
import com.example.tasker.receiver.AlarmReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    private var alarmManager: AlarmManager? = null

    fun initAlarmReceiver(context: Context) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun createProfile(hour: Int, minute: Int, context: Context) {
        val profile = Profile(hour = hour, minute = minute)
        val db = ProfileDatabase.getDatabase(context)

        viewModelScope.launch(Dispatchers.IO) {
            db.profileDao().insert(profile)
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            hour * 100 + minute,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
