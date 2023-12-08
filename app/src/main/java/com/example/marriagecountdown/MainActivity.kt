package com.example.marriagecountdown

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.ZoneOffset

class MainActivity : AppCompatActivity() {

    private lateinit var editContainer: LinearLayout
    private var selectedDate: LocalDateTime = LocalDateTime.of(2024, 2, 15, 8, 0)
    private lateinit var countdownDays: TextView
    private lateinit var countdownHours: TextView
    private lateinit var countdownMinutes: TextView
    private lateinit var countdownSeconds: TextView
    private lateinit var dateTimePickerManager: DateTimePickerManager
    private lateinit var updateHandler:Handler
    private var updateRunnable: Runnable = object : Runnable {
        override fun run() {
            updateCountdown(selectedDate)
            updateHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        setupDateTimePicker()
        setupUpdateHandler()
    }

    private fun bindViews() {
        editContainer = findViewById(R.id.edit_container)
        countdownDays = findViewById(R.id.days)
        countdownHours = findViewById(R.id.hours)
        countdownMinutes = findViewById(R.id.minutes)
        countdownSeconds = findViewById(R.id.seconds)
    }

    private fun setupDateTimePicker() {
        val finishDateSelection: (LocalDateTime) -> Unit = { selectedDateTime ->
            updateCountdown(selectedDateTime)
            selectedDate = selectedDateTime
        }

        dateTimePickerManager = DateTimePickerManager(this, finishDateSelection)

        editContainer.setOnClickListener {
            dateTimePickerManager.selectDateTime(selectedDate)
        }
    }

    private fun setupUpdateHandler() {
        updateHandler = Handler(Looper.getMainLooper())
        updateHandler.post(updateRunnable)
    }

    private fun updateCountdown(selectedDateTime: LocalDateTime) {
        val currentDate = LocalDateTime.now()

        selectedDateTime.let {
            val timeDiff = it.toEpochSecond(ZoneOffset.UTC) - currentDate.toEpochSecond(ZoneOffset.UTC)
            val seconds = timeDiff
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            countdownDays.text = fillNumber(days)
            countdownHours.text = fillNumber(hours % 24)
            countdownMinutes.text = fillNumber(minutes % 60)
            countdownSeconds.text = fillNumber(seconds % 60)
        }
    }

    private fun fillNumber(num: Long): String{
        return num.toString().padStart(2, '0')
    }
}
