package ru.academy.homework10

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver by lazy { EventBroadcastReceiver() }
    private var b: Boolean = false
    private lateinit var switch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.stopService).setOnClickListener {
            stopService(Intent(this@MainActivity, ServiceExample::class.java))
        }

        switch = findViewById<SwitchCompat>(R.id.switcher)
        switch.isChecked = loadBooleanStatement()

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "External Storage", Toast.LENGTH_SHORT).show()
                b = true
                saveBooleanStatement(b)
            } else {
                Toast.makeText(this, "Internal Storage", Toast.LENGTH_SHORT).show()
                b = false
                saveBooleanStatement(b)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        findViewById<Button>(R.id.startService).setOnClickListener {
            val intentFilter = IntentFilter().apply {
                addAction(Intent.ACTION_TIME_CHANGED)
                addAction(Intent.ACTION_TIME_TICK)
                addAction(Intent.ACTION_TIMEZONE_CHANGED)
            }
            registerReceiver(broadcastReceiver, intentFilter)
            startService(Intent(this@MainActivity, ServiceExample::class.java))
        }
    }

    private fun saveBooleanStatement(b: Boolean) {
        val sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        var ed: SharedPreferences.Editor = sharedPreferences.edit()
        ed.putBoolean("STATEMENT", b)
        ed.apply()
    }

    private fun loadBooleanStatement(): Boolean {
        val sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("STATEMENT", false)
    }
}
