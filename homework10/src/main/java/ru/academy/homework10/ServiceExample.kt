package ru.academy.homework10

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.concurrent.TimeUnit

class ServiceExample : Service(), ServiceActions {

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG, "Сработал метод onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG, "Сработал метод onStartCommand")
        someJob(startId, intent)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
     return ServiceBinder()
    }

    inner class ServiceBinder() : Binder() {
        fun getServiceActions(): ServiceActions =
                this@ServiceExample
    }

    private fun someJob(startId: Int, intent: Intent?) {
        val runnable = Runnable {
        val event: EventBroadcastReceiver = EventBroadcastReceiver()
            Log.d(LOG, "someJob started: $startId")
        }
        Thread(runnable).start()
    }


    override fun getData(): Int {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG, "Сработал метод onDestroy")
    }

    companion object {
        private val LOG = "ServiceExample"
    }
}
