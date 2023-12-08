package com.example.marriagecountdown

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var editContainer: LinearLayout
    private var selectedDate: LocalDateTime = LocalDateTime.of(2024, 2, 15, 8, 0)
    private lateinit var countdownManager: CountdownManager
    private lateinit var dateTimePickerManager: DateTimePickerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        setupDateTimePicker()
        setupCountdownManager()
    }

    private fun bindViews() {
        editContainer = findViewById(R.id.edit_container)
    }

    private fun setupDateTimePicker() {
        val finishDateSelection: (LocalDateTime) -> Unit = { selectedDateTime ->
            countdownManager.stop()
            countdownManager.start(selectedDateTime)
            selectedDate = selectedDateTime
        }

        val countdownViews = CountdownViews(
            findViewById(R.id.days),
            findViewById(R.id.hours),
            findViewById(R.id.minutes),
            findViewById(R.id.seconds)
        )

        countdownManager = CountdownManager(countdownViews)
        dateTimePickerManager = DateTimePickerManager(this, finishDateSelection)

        editContainer.setOnClickListener {
            dateTimePickerManager.selectDateTime(selectedDate)
        }
    }

    private fun setupCountdownManager() {
        countdownManager.start(selectedDate)
    }
}
