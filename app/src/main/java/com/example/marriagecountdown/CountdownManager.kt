package com.example.marriagecountdown

import android.os.Handler
import android.os.Looper
import java.time.LocalDateTime
import java.time.ZoneOffset

class CountdownManager(private val countdownViews: CountdownViews) {

    private lateinit var updateHandler: Handler
    private lateinit var updateRunnable: Runnable

    fun start(selectedDate: LocalDateTime) {
        updateHandler = Handler(Looper.getMainLooper())
        updateRunnable = object : Runnable {
            override fun run() {
                updateCountdown(selectedDate)
                updateHandler.postDelayed(this, 1000)
            }
        }
        updateHandler.post(updateRunnable)
    }

    fun stop() {
        updateHandler.removeCallbacks(updateRunnable)
    }

    private fun updateCountdown(selectedDateTime: LocalDateTime) {
        val currentDate = LocalDateTime.now()

        selectedDateTime.let {
            val timeDiff = it.toEpochSecond(ZoneOffset.UTC) - currentDate.toEpochSecond(ZoneOffset.UTC)
            val seconds = timeDiff
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            countdownViews.daysTextView.text = fillNumber(days)
            countdownViews.hoursTextView.text = fillNumber(hours % 24)
            countdownViews.minutesTextView.text = fillNumber(minutes % 60)
            countdownViews.secondsTextView.text = fillNumber(seconds % 60)
        }
    }

    private fun fillNumber(num: Long): String {
        return num.toString().padStart(2, '0')
    }
}
