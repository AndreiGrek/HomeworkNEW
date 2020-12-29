package ru.academy.homework10

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver by lazy { EventBroadcastReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startService).setOnClickListener{
            var intent: Intent = getIntent()
            var s: String = getIntent().getStringExtra("f")
            startService(Intent(this@MainActivity, ServiceExample::class.java))

//            val intent = Intent().apply {
//                action = "android.intent.action.TIMEZONE_CHANGED"
//                putExtra("KEY", "sadasda")
//                addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
//            }
//            sendBroadcast(intent)
        }

        findViewById<Button>(R.id.stopService).setOnClickListener{
            stopService(Intent(this@MainActivity, ServiceExample::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }
}
