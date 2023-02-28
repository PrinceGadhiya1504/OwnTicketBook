package com.example.ownticketbook

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormatterBuilder
import java.text.DateFormat
import java.time.format.DateTimeFormatter.ofPattern

class BookedSeatsActivity : AppCompatActivity()
{
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_sheats)

        var Date = intent.getStringExtra("Date")

        var movieName = intent.getStringExtra("Name")
        var btn9 = intent.getStringExtra("9am")
        var btn3 = intent.getStringExtra("3am")

        val lblMovieName = findViewById<TextView>(R.id.lblmoviename)
        val date = findViewById<TextView>(R.id.date)
        val btn9am = findViewById<TextView>(R.id.btn)
        val btn3am = findViewById<TextView>(R.id.btn)


        lblMovieName.text = movieName
        date.text = Date
        btn9am.setText(btn9)
        btn3am.setText(btn3)

    }
}