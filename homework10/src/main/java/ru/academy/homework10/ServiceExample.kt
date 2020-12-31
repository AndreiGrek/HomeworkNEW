package ru.academy.homework10

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

class ServiceExample : Service(), ServiceActions {

    private val actionFile: String = "ActionFile.txt"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG, "Сработал метод onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG, "Сработал метод onStartCommand")
        val bl: Bundle? = intent?.extras
        val event: String? = bl?.getString("EVENT")
        someJob(event)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return ServiceBinder()
    }

    inner class ServiceBinder() : Binder() {
        fun getServiceActions(): ServiceActions =
                this@ServiceExample
    }

    private fun someJob(event: String?) {
        val runnable = Runnable {
            if (event != null) {
                if (loadBooleanStatement()) {
                    Log.d(LOG, "Записано в External Storage")
                    writeToExternal(event)
                } else if (!loadBooleanStatement()) {
                    Log.d(LOG, "Записано в Internal Storage")
                    writeToInternal(event)
                }
            }
        }
        Thread(runnable).start()
    }

    private fun writeToInternal(event: String?) {
        var date = Date(System.currentTimeMillis())
        var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val currentDate: String = formatter.format(date)
        val bw = BufferedWriter(OutputStreamWriter(
                openFileOutput(actionFile, Context.MODE_APPEND)))
        bw.write(currentDate + " - " + event + "\n")
        bw.close()
    }

    private fun writeToExternal(event: String?) {
        var date = Date(System.currentTimeMillis())
        var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val currentDate: String = formatter.format(date)
        var externalPath = File(Environment.getExternalStorageDirectory().absolutePath, "Actions")
        externalPath.mkdir()
        var externalFile = File(externalPath, actionFile)
        val bw = BufferedWriter(FileWriter(externalFile, true))
        bw.write(currentDate + " - " + event + "\n")
        bw.close()
    }

    override fun getData(): Int {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG, "Сработал метод onDestroy")
    }

    private fun loadBooleanStatement(): Boolean {
        val sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("STATEMENT", false)
    }

    companion object {
        private val LOG = "ServiceExample"
    }
}
