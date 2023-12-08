package com.example.marriagecountdown

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class DateTimePickerManager(
    private val context: Context,
    private val onDateTimeSelected: (LocalDateTime) -> Unit
) {

    public fun selectDateTime(initialDateTime:LocalDateTime?){
        showDatePickerDialog(initialDateTime)
    }

    private fun showDatePickerDialog(initialDateTime: LocalDateTime?) {
        val calendar = initialDateTime?.toLocalDate() ?: LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                val locSelectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                val selectedDateTime = locSelectedDate.atTime(initialDateTime?.toLocalTime())
                showTimePickerDialog(selectedDateTime)
            },
            calendar.year,
            calendar.monthValue - 1,
            calendar.dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog(initialDateTime: LocalDateTime?) {
        val calendar = initialDateTime ?: LocalDateTime.now()

        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val selectedTime = LocalDateTime.of(calendar.toLocalDate(), LocalTime.of(hourOfDay, minute))
                onDateTimeSelected(selectedTime)
            },
            calendar.hour,
            calendar.minute,
            true
        )
        timePickerDialog.show()
    }

}