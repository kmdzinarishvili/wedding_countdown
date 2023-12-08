package com.example.marriagecountdown
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var editContainer: LinearLayout? = null
    private var selectedDate: Date? = Date(124, 1, 15, 8, 0)
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
        updateCountdown();
        editContainer?.setOnClickListener{
            showDatePickerDialog()
        }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        selectedDate?.let { calendar.time = it }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                selectedDate = calendar.time
                showTimePickerDialog()
            },
            year,
            month,
            day,
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                selectedDate = calendar.time
                updateCountdown()
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }


    private fun updateCountdown() {
        val currentDate = Calendar.getInstance().time

        selectedDate?.let { selectedDate ->
            val timeDiff = selectedDate.time - currentDate.time
            val seconds = timeDiff / 1000
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
