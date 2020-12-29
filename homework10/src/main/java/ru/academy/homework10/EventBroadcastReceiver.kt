package ru.academy.homework10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

class EventBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: ""
        when (action) {
            Intent.ACTION_TIMEZONE_CHANGED -> Log.d(LOG, "Сработала смена часового пояса")
            Intent.ACTION_TIME_CHANGED -> Log.d(LOG, "Сработала смена времени")
              Intent.ACTION_TIME_TICK -> sendTask ("Сработала смена минут")
//            Intent.ACTION_TIME_TICK -> Toast.makeText(context, "Бродкаст сработал", Toast.LENGTH_SHORT).show();
        }
    }

    private fun sendTask (s : String): String {
        val intent: Intent = Intent()
        return s
    }

//    private fun print(msg: String) {
//        Log.d("EventBroadcast", "onReceive $msg")
//        Log.d("EventBroadcast", "Thread name ${Thread.currentThread().name}")
//    }

    companion object {
        private val LOG = "ServiceExample"
    }
}