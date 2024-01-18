package com.example.groovy

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.library.Myclass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            setBackgroundColor(Color.WHITE)
            gravity = Gravity.CENTER
            orientation = LinearLayout.VERTICAL
        }

        val textView = TextView(this).apply {
            text = "Initial Text"
            gravity = Gravity.CENTER
            textSize = 20f
        }

        val button = Button(this).apply {
            text = "Click Me"
            setBackgroundColor(Color.BLUE)
            setTextColor(Color.WHITE)
            setOnClickListener {
                val myClassInstance = Myclass(this@MainActivity)
                myClassInstance.whistleLoopEvents("eventName", "extraParameters")
            }
        }
        layout.addView(textView)
        layout.addView(button)
        setContentView(layout)
    }
}