package com.example.marriagecountdown

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private var editContainer: LinearLayout? = null
    private var selectedDate: LocalDateTime? = LocalDateTime.of(2024, 2, 15, 8, 0)
    private var countdownDays: TextView? = null
    private var countdownHours: TextView? = null
    private var countdownMinutes: TextView? = null
    private var countdownSeconds: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editContainer = findViewById(R.id.edit_container)
        countdownDays = findViewById(R.id.days)
        countdownHours = findViewById(R.id.hours)
        countdownMinutes = findViewById(R.id.minutes)
        countdownSeconds = findViewById(R.id.seconds)
        updateCountdown()
        editContainer?.setOnClickListener{
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = selectedDate?.toLocalDate() ?: LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val locSelectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                val selectedDateTime = locSelectedDate.atTime(selectedDate?.toLocalTime())
                this.selectedDate = selectedDateTime
                showTimePickerDialog()
            },
            calendar.year,
            calendar.monthValue - 1,
            calendar.dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = selectedDate ?: LocalDateTime.now()

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = LocalDateTime.of(calendar.toLocalDate(), LocalTime.of(hourOfDay, minute))
                selectedDate = selectedTime
                updateCountdown()
            },
            calendar.hour,
            calendar.minute,
            true
        )
        timePickerDialog.show()
    }

    private fun updateCountdown() {
        val currentDate = LocalDateTime.now()

        selectedDate?.let { selectedDateTime ->
            val timeDiff = selectedDateTime.toEpochSecond(ZoneOffset.UTC) - currentDate.toEpochSecond(ZoneOffset.UTC)
            val seconds = timeDiff
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            countdownDays?.text = fillNumber(days)
            countdownHours?.text = fillNumber(hours % 24)
            countdownMinutes?.text = fillNumber(minutes % 60)
            countdownSeconds?.text = fillNumber(seconds % 60)

            Handler(Looper.getMainLooper()).postDelayed({
                updateCountdown()
            }, 1000)
        }
    }

    private fun fillNumber(num: Long): String{
        return num.toString().padStart(2, '0')
    }
}
