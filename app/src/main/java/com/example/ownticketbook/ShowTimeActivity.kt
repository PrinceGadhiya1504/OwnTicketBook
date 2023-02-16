package com.example.ownticketbook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ownticketbook.models.Movie
import com.example.ownticketbook.services.MoviesService
import com.google.gson.Gson
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.util.*

class ShowTimeActivity : AppCompatActivity()
{

    private lateinit var dayPicker: DayScrollDatePicker
    private lateinit var selectDate: String
    private lateinit var moviesService: MoviesService
    private lateinit var movie: Movie
    private lateinit var Disciption: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_time)

        Disciption = findViewById(R.id.txtdescription)

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dayPicker = findViewById(R.id.day_date_picker)
        dayPicker.setStartDate(day, month + 1, year)
        dayPicker.getSelectedDate { date -> selectDate = date.toString() }

        findViewById<Button>(R.id.btn9am).let {
            it.setOnClickListener {
                val intent = Intent(this, BookedSeatsActivity::class.java)
                startActivity(intent)
            }
        }
        findViewById<Button>(R.id.btn3am).let {
            it.setOnClickListener {
                val intent = Intent(this, BookedSeatsActivity::class.java)
                startActivity(intent)
            }
        }
        findViewById<Button>(R.id.btn12am).let {
            it.setOnClickListener {
                val intent = Intent(this, BookedSeatsActivity::class.java)
                startActivity(intent)
            }
        }
        findViewById<Button>(R.id.btn6am).let {
            it.setOnClickListener {
                val intent = Intent(this, BookedSeatsActivity::class.java)
                startActivity(intent)
            }
        }
        configureData()

    }

    private fun configureData()
    {
        CoroutineScope(Dispatchers.IO).launch {
            moviesService = MoviesService()
            val response = moviesService.getAllMovie()
            if (response.code == HttpURLConnection.HTTP_OK)
            {
                movie = Gson().fromJson(response.message, Movie::class.java)

//                val name = movies.Name
//                val Desciption = movies.Description

                withContext(Dispatchers.Main) {
                    Disciption.text = Disciption.toString()
                }
            }
        }
    }

}
