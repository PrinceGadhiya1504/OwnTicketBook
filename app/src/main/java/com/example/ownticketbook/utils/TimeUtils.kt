package com.example.ownticketbook.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun formatAsDate(date: Date): String
        {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            return formatter.format(date)
        }
    }
}