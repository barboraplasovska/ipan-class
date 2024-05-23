package com.example.counterapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.max


class MainActivity : AppCompatActivity() {

    private lateinit var counterTextView: TextView
    private lateinit var clickMeButton: Button
    private lateinit var resetButton: Button
    private lateinit var reverseButton: Button
    private lateinit var normalButton: Button
    private lateinit var bigButton: Button

    private var counter = 0
    private var isUpMode = true // true for up, false for down

    private var incrementSize = 1 // 1 for normal, 3 for big

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        counterTextView = findViewById(R.id.counter_textview);
        clickMeButton = findViewById(R.id.clickmebutton);
        resetButton = findViewById(R.id.resetbutton);
        reverseButton = findViewById(R.id.reversebutton);
        normalButton = findViewById(R.id.normalbutton);
        bigButton = findViewById(R.id.bigbutton);

        clickMeButton.setOnClickListener(View.OnClickListener { updateCounter() })

        resetButton.setOnClickListener(View.OnClickListener { resetCounter() })

        reverseButton.setOnClickListener(View.OnClickListener { isUpMode = !isUpMode })

        normalButton.setOnClickListener(View.OnClickListener { incrementSize = 1 })

        bigButton.setOnClickListener(View.OnClickListener { incrementSize = 3 })
    }

    private fun updateCounter() {
        if (isUpMode) {
            counter += incrementSize
        } else {
            counter = max(0.0, (counter - incrementSize).toDouble()).toInt()
        }
        counterTextView.text = counter.toString()
    }

    private fun resetCounter() {
        counter = 0
        counterTextView.text = counter.toString()
    }
}