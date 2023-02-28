package com.example.ownticketbook

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ownticketbook.adapters.MoviesAdapter
import com.example.ownticketbook.models.Movie
import com.example.ownticketbook.services.MoviesService
import com.example.ownticketbook.utils.ApiRequest
import com.google.gson.Gson
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.*

class ShowTimeActivity : AppCompatActivity()
{

    private lateinit var dayPicker: DayScrollDatePicker
    private lateinit var selectDate: String
    private lateinit var moviesService: MoviesService
    private var movieId: Int = 0
    private lateinit var lblMovieName: TextView
    private lateinit var Disciption: TextView
    private lateinit var Image: ImageView
    private lateinit var btn9am: Button
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_time)

        movieId = intent.getIntExtra("Id", 0)

        Disciption = findViewById(R.id.lblDescription)
        lblMovieName = findViewById(R.id.txtmoviename)
        Image = findViewById(R.id.image)

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dayPicker = findViewById(R.id.day_date_picker)
        dayPicker.setStartDate(day, month + 1, year)
        dayPicker.getSelectedDate { date -> selectDate = date.toString() }

//        findViewById<Button>(R.id.btn3am).let {
//            it.setOnClickListener {
//                val intent = Intent(this, BookedSeatsActivity::class.java)
//                startActivity(intent)
//            }
//        }
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

    @SuppressLint("SimpleDateFormat")
    private fun configureData()
    {
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
                    val Desciption = movie.Description
                    withContext(Dispatchers.Main)
                    {
                        lblMovieName.text = name

                        Disciption.text = Desciption
                        Glide.with(this@ShowTimeActivity).load(ImageName).into(Image)

                        findViewById<Button>(R.id.btn9am).setOnClickListener {
                            val intent = Intent(this@ShowTimeActivity, BookedSeatsActivity::class.java)
                            intent.putExtra("Name", name)
                            intent.putExtra("Date",selectDate)
                                intent.putExtra("9am","9am")
                            startActivity(intent)

                        }

                        findViewById<Button>(R.id.btn3am).setOnClickListener {
                            val intent = Intent(this@ShowTimeActivity, BookedSeatsActivity::class.java)
                            intent.putExtra("Name", name)
                            intent.putExtra("Date",selectDate)
                            intent.putExtra("3am","3am")
                            startActivity(intent)

                        }

                    }
                }
            }
        }
    }

}
