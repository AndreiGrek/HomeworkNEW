package ru.academy.homework10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class EventBroadcastReceiver : BroadcastReceiver() {

    private val eventTick: String = "ACTION_TIME_TICK"
    private val eventTime: String = "ACTION_TIME_CHANGED"
    private val eventTimeZone: String = "ACTION_TIMEZONE_CHANGED"

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: ""
        when (action) {
            Intent.ACTION_TIME_TICK -> sendTaskToservice(eventTick, context)
            Intent.ACTION_TIME_CHANGED -> sendTaskToservice(eventTime, context)
            Intent.ACTION_TIMEZONE_CHANGED -> sendTaskToservice(eventTimeZone, context)
//            Intent.ACTION_TIME_TICK -> Toast.makeText(context, "Бродкаст сработал", Toast.LENGTH_SHORT).show();
        }
    }

    private fun sendTaskToservice(event: String, context: Context?) {
        val intent = Intent(context, ServiceExample::class.java)
        intent.putExtra("EVENT", event)
        context?.startService(intent)
    }
}