package com.example.ownticketbook

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.ownticketbook.models.Movie
import com.example.ownticketbook.services.MoviesService
import com.example.ownticketbook.utils.ApiRequest
import com.google.gson.Gson
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormatterBuilder
import java.net.HttpURLConnection
import java.text.DateFormat
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

class BookedSeatsActivity : AppCompatActivity()
{
    private lateinit var dayPicker: DayScrollDatePicker
    private lateinit var selectDate: String
    private lateinit var moviesService: MoviesService
    private var movieId: Int = 0
    private lateinit var Image: ImageView
    private lateinit var lblMovieName: TextView
    private lateinit var btnfirstshowtime: Button
    private lateinit var btnsecondshowtime: Button

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_sheats)

        movieId = intent.getIntExtra("Id", 0)
        lblMovieName = findViewById(R.id.lblmoviename)
        btnfirstshowtime = findViewById(R.id.btnfirstshowtime)
        btnsecondshowtime = findViewById(R.id.btnsecondshowtime)
        Image = findViewById(R.id.movieimage)

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dayPicker = findViewById(R.id.day_date_picker)
        dayPicker.setStartDate(day, month + 1, year)
        dayPicker.getSelectedDate { date -> selectDate = date.toString() }

        CoroutineScope(Dispatchers.IO).launch {
                moviesService = MoviesService()
                val response = moviesService.getMovie(movieId)
                if (response.code == HttpURLConnection.HTTP_OK)
                {
                    val movies = Gson().fromJson(response.message, Array<Movie>::class.java)
                    for (movie in movies)
                    {
                        val name = movie.Name
                        val ImageName = "${ApiRequest.IMAGE_URL}/${movie.ImageName}"
                        val FirstShoeTime = movie.FirstShowTime
                        val SecondShowTime = movie.SecondShowTime

                        withContext(Dispatchers.Main)
                        {
                            lblMovieName.text = name
                            Glide.with(this@BookedSeatsActivity).load(ImageName).into(Image)
                            btnfirstshowtime.text = FirstShoeTime
                            btnsecondshowtime.text = SecondShowTime
                        }
                    }
            }
        }
    }
}