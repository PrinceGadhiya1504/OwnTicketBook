package com.example.ownticketbook

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ownticketbook.models.Booking
import com.example.ownticketbook.models.Movie
import com.example.ownticketbook.models.Seat
import com.example.ownticketbook.services.BookingService
import com.example.ownticketbook.services.MoviesService
import com.example.ownticketbook.utils.ApiRequest
import com.example.ownticketbook.utils.TimeUtils
import com.google.gson.Gson
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
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
    private lateinit var btnBookNow: Button
    private lateinit var time: String
    private lateinit var date: String
    private lateinit var a1: CheckBox
    private lateinit var a2: CheckBox
    private lateinit var a3: CheckBox
    private lateinit var a4: CheckBox
    private lateinit var a5: CheckBox
    private lateinit var a6: CheckBox
    private lateinit var b1: CheckBox
    private lateinit var b2: CheckBox
    private lateinit var b3: CheckBox
    private lateinit var b4: CheckBox
    private lateinit var b5: CheckBox
    private lateinit var b6: CheckBox
    private lateinit var c1: CheckBox
    private lateinit var c2: CheckBox
    private lateinit var c3: CheckBox
    private lateinit var c4: CheckBox
    private lateinit var c5: CheckBox
    private lateinit var c6: CheckBox
    private lateinit var d1: CheckBox
    private lateinit var d2: CheckBox
    private lateinit var d3: CheckBox
    private lateinit var d4: CheckBox
    private lateinit var d5: CheckBox
    private lateinit var d6: CheckBox
    private lateinit var e1: CheckBox
    private lateinit var e2: CheckBox
    private lateinit var e3: CheckBox
    private lateinit var e4: CheckBox
    private lateinit var e5: CheckBox
    private lateinit var e6: CheckBox
    private lateinit var f1: CheckBox
    private lateinit var f2: CheckBox
    private lateinit var f3: CheckBox
    private lateinit var f4: CheckBox
    private lateinit var f5: CheckBox
    private lateinit var f6: CheckBox
    private lateinit var seats: ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_sheats)

        movieId = intent.getIntExtra("Id", 0)
        lblMovieName = findViewById(R.id.lblmoviename)
        btnfirstshowtime = findViewById(R.id.btnfirstshowtime)
        btnsecondshowtime = findViewById(R.id.btnsecondshowtime)
        btnBookNow = findViewById(R.id.btnbooknow)
        seats = ArrayList()
        btnBookNow.setOnClickListener {
            bookSeat()
        }
        /*
        seats.plus("value")
        */
        Image = findViewById(R.id.movieimage)
        initCheckBox()

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
                    val FirstShowDate = movie.ReleaseDate
                    val SecondShowDate = movie.ReleaseDate

                    withContext(Dispatchers.Main)
                    {
                        lblMovieName.text = name
                        Glide.with(this@BookedSeatsActivity).load(ImageName).into(Image)
                        btnfirstshowtime.text = FirstShoeTime
                        btnsecondshowtime.text = SecondShowTime
                        btnfirstshowtime.setOnClickListener {
                            clearCheckBox()
                            time = FirstShoeTime
                            date = FirstShowDate
                            btnfirstshowtime.setBackgroundColor(Color.WHITE)
                            btnsecondshowtime.setBackgroundColor(getColor(R.color.teal_200))

                        }
                        btnsecondshowtime.setOnClickListener {
                            clearCheckBox()
                            time = SecondShowTime
                            date = SecondShowDate
                            btnsecondshowtime.setBackgroundColor(Color.WHITE)
                            btnfirstshowtime.setBackgroundColor(getColor(R.color.teal_200))
                        }
                    }
                }
            }
        }
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dayPicker = findViewById(R.id.day_date_picker)
        dayPicker.setStartDate(day, month + 1, year)
        dayPicker.getSelectedDate { dateq ->
            if (dateq == null)
                return@getSelectedDate

            selectDate = TimeUtils.formatAsDate(dateq)
            // -----------------------
            val dateString = dateq
            val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
            val date = formatter.parse(dateString.toString())
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
            getSeats(formattedDate)
        }

        // On click listener for the  checkbox to add or remove the seat number from the list
        onClickCheckBoxLister()
    }

    private fun onClickCheckBoxLister(){
        a1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("A1")
            } else
            {
                seats.remove("A1")
            }
        }

        a2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("A2")
            } else
            {
                seats.remove("A2")
            }
        }

        a3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("A3")
            } else
            {
                seats.remove("A3")
            }
        }

        a4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("A4")
            } else
            {
                seats.remove("A4")
            }
        }

        a5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("A5")
            } else
            {
                seats.remove("A5")
            }
        }

        a6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("A6")
            } else
            {
                seats.remove("A6")
            }
        }

        b1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("B1")
            } else
            {
                seats.remove("B1")
            }
        }

        b2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("B2")
            } else
            {
                seats.remove("B2")
            }
        }

        b3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("B3")
            } else
            {
                seats.remove("B3")
            }
        }

        b4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("B4")
            } else
            {
                seats.remove("B4")
            }
        }

        b5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("B5")
            } else
            {
                seats.remove("B5")
            }
        }

        b6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("B6")
            } else
            {
                seats.remove("B6")
            }
        }

        c1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("C1")
            } else
            {
                seats.remove("C1")
            }
        }

        c2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("C2")
            } else
            {
                seats.remove("C2")
            }
        }

        c3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("C3")
            } else
            {
                seats.remove("C3")
            }
        }

        c4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("C4")
            } else
            {
                seats.remove("C4")
            }
        }

        c5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("C5")
            } else
            {
                seats.remove("C5")
            }
        }

        c6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("C6")
            } else
            {
                seats.remove("C6")
            }
        }

        d1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("D1")
            } else
            {
                seats.remove("D1")
            }
        }

        d2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("D2")
            } else
            {
                seats.remove("D2")
            }
        }

        d3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("D3")
            } else
            {
                seats.remove("D3")
            }
        }

        d4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("D4")
            } else
            {
                seats.remove("D4")
            }
        }

        d5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("D5")
            } else
            {
                seats.remove("D5")
            }
        }

        d6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("D6")
            } else
            {
                seats.remove("D6")
            }
        }

        e1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("E1")
            } else
            {
                seats.remove("E1")
            }
        }

        e2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("E2")
            } else
            {
                seats.remove("E2")
            }
        }

        e3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("E3")
            } else
            {
                seats.remove("E3")
            }
        }

        e4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("E4")
            } else
            {
                seats.remove("E4")
            }
        }

        e5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("E5")
            } else
            {
                seats.remove("E5")
            }
        }

        e6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("E6")
            } else
            {
                seats.remove("E6")
            }
        }

        f1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("F1")
            } else
            {
                seats.remove("F1")
            }
        }

        f2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("F2")
            } else
            {
                seats.remove("F2")
            }
        }

        f3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("F3")
            } else
            {
                seats.remove("F3")
            }
        }

        f4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("F4")
            } else
            {
                seats.remove("F4")
            }
        }

        f5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("F5")
            } else
            {
                seats.remove("F5")
            }
        }

        f6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                seats.add("F6")
            } else
            {
                seats.remove("F6")
            }
        }
    }

    private fun initCheckBox(){
        a1 = findViewById(R.id.seatA1)
        a2 = findViewById(R.id.seatA2)
        a3 = findViewById(R.id.seatA3)
        a4 = findViewById(R.id.seatA4)
        a5 = findViewById(R.id.seatA5)
        a6 = findViewById(R.id.seatA6)
        b1 = findViewById(R.id.seatB1)
        b2 = findViewById(R.id.seatB2)
        b3 = findViewById(R.id.seatB3)
        b4 = findViewById(R.id.seatB4)
        b5 = findViewById(R.id.seatB5)
        b6 = findViewById(R.id.seatB6)
        c1 = findViewById(R.id.seatC1)
        c2 = findViewById(R.id.seatC2)
        c3 = findViewById(R.id.seatC3)
        c4 = findViewById(R.id.seatC4)
        c5 = findViewById(R.id.seatC5)
        c6 = findViewById(R.id.seatC6)
        d1 = findViewById(R.id.seatD1)
        d2 = findViewById(R.id.seatD2)
        d3 = findViewById(R.id.seatD3)
        d4 = findViewById(R.id.seatD4)
        d5 = findViewById(R.id.seatD5)
        d6 = findViewById(R.id.seatD6)
        e1 = findViewById(R.id.seatE1)
        e2 = findViewById(R.id.seatE2)
        e3 = findViewById(R.id.seatE3)
        e4 = findViewById(R.id.seatE4)
        e5 = findViewById(R.id.seatE5)
        e6 = findViewById(R.id.seatE6)
        f1 = findViewById(R.id.seatF1)
        f2 = findViewById(R.id.seatF2)
        f3 = findViewById(R.id.seatF3)
        f4 = findViewById(R.id.seatF4)
        f5 = findViewById(R.id.seatF5)
        f6 = findViewById(R.id.seatF6)
    }

    private fun getSeats(date: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val bookingService = BookingService()
            val response = bookingService.getBookedSeats(time, date)

            val seats = Gson().fromJson(response.message, Array<Seat>::class.java)

            for (seat in seats)
            {
                withContext(Dispatchers.Main) {
                    if (seat.seatNumber == "A1")
                    {
                        a1.isChecked = true
                        a1.isEnabled = false
                    }
                    if (seat.seatNumber == "A2")
                    {
                        a2.isChecked = true
                        a2.isEnabled = false
                    }
                    if (seat.seatNumber == "A3")
                    {
                        a3.isChecked = true
                        a3.isEnabled = false
                    }
                    if (seat.seatNumber == "A4")
                    {
                        a4.isChecked = true
                        a4.isEnabled = false
                    }
                    if (seat.seatNumber == "A5")
                    {
                        a5.isChecked = true
                        a5.isEnabled = false
                    }
                    if (seat.seatNumber == "A6")
                    {
                        a6.isChecked = true
                        a6.isEnabled = false
                    }
                    if (seat.seatNumber == "B1")
                    {
                        b1.isChecked = true
                        b1.isEnabled = false
                    }
                    if (seat.seatNumber == "B2")
                    {
                        b2.isChecked = true
                        b2.isEnabled = false
                    }
                    if (seat.seatNumber == "B3")
                    {
                        b3.isChecked = true
                        b3.isEnabled = false
                    }
                    if (seat.seatNumber == "B4")
                    {
                        b4.isChecked = true
                        b4.isEnabled = false
                    }
                    if (seat.seatNumber == "B5")
                    {
                        b5.isChecked = true
                        b5.isEnabled = false
                    }
                    if (seat.seatNumber == "B6")
                    {
                        b6.isChecked = true
                        b6.isEnabled = false
                    }
                    if (seat.seatNumber == "C1")
                    {
                        c1.isChecked = true
                        c1.isEnabled = false
                    }
                    if (seat.seatNumber == "C2")
                    {
                        c2.isChecked = true
                        c2.isEnabled = false
                    }
                    if (seat.seatNumber == "C3")
                    {
                        c3.isChecked = true
                        c3.isEnabled = false
                    }
                    if (seat.seatNumber == "C4")
                    {
                        c4.isChecked = true
                        c4.isEnabled = false
                    }
                    if (seat.seatNumber == "C5")
                    {
                        c5.isChecked = true
                        c5.isEnabled = false
                    }
                    if (seat.seatNumber == "C6")
                    {
                        c6.isChecked = true
                        c6.isEnabled = false
                    }
                    if (seat.seatNumber == "D1")
                    {
                        d1.isChecked = true
                        d1.isEnabled = false
                    }
                    if (seat.seatNumber == "D2")
                    {
                        d2.isChecked = true
                        d2.isEnabled = false
                    }
                    if (seat.seatNumber == "D3")
                    {
                        d3.isChecked = true
                        d3.isEnabled = false
                    }
                    if (seat.seatNumber == "D4")
                    {
                        d4.isChecked = true
                        d4.isEnabled = false
                    }
                    if (seat.seatNumber == "D5")
                    {
                        d5.isChecked = true
                        d5.isEnabled = false
                    }
                    if (seat.seatNumber == "D6")
                    {
                        d6.isChecked = true
                        d6.isEnabled = false
                    }
                    if (seat.seatNumber == "E1")
                    {
                        e1.isChecked = true
                        e1.isEnabled = false
                    }
                    if (seat.seatNumber == "E2")
                    {
                        e2.isChecked = true
                        e2.isEnabled = false
                    }
                    if (seat.seatNumber == "E3")
                    {
                        e3.isChecked = true
                        e3.isEnabled = false
                    }
                    if (seat.seatNumber == "E4")
                    {
                        e4.isChecked = true
                        e4.isEnabled = false
                    }
                    if (seat.seatNumber == "E5")
                    {
                        e5.isChecked = true
                        e5.isEnabled = false
                    }
                    if (seat.seatNumber == "E6")
                    {
                        e6.isChecked = true
                        e6.isEnabled = false
                    }
                    if (seat.seatNumber == "F1")
                    {
                        f1.isChecked = true
                        f1.isEnabled = false
                    }
                    if (seat.seatNumber == "F2")
                    {
                        f2.isChecked = true
                        f2.isEnabled = false
                    }
                    if (seat.seatNumber == "F3")
                    {
                        f3.isChecked = true
                        f3.isEnabled = false
                    }
                    if (seat.seatNumber == "F4")
                    {
                        f4.isChecked = true
                        f4.isEnabled = false
                    }
                    if (seat.seatNumber == "F5")
                    {
                        f5.isChecked = true
                        f5.isEnabled = false
                    }
                    if (seat.seatNumber == "F6")
                    {
                        f6.isChecked = true
                        f6.isEnabled = false
                    }
                }
            }
        }
    }

    private fun clearCheckBox()
    {
        a1.isChecked = false
        a2.isChecked = false
        a3.isChecked = false
        a4.isChecked = false
        a5.isChecked = false
        a6.isChecked = false
        b1.isChecked = false
        b2.isChecked = false
        b3.isChecked = false
        b4.isChecked = false
        b5.isChecked = false
        b6.isChecked = false
        c1.isChecked = false
        c2.isChecked = false
        c3.isChecked = false
        c4.isChecked = false
        c5.isChecked = false
        c6.isChecked = false
        d1.isChecked = false
        d2.isChecked = false
        d3.isChecked = false
        d4.isChecked = false
        d5.isChecked = false
        d6.isChecked = false
        e1.isChecked = false
        e2.isChecked = false
        e3.isChecked = false
        e4.isChecked = false
        e5.isChecked = false
        e6.isChecked = false
        f1.isChecked = false
        f2.isChecked = false
        f3.isChecked = false
        f4.isChecked = false
        f5.isChecked = false
        f6.isChecked = false
        a1.isEnabled = true
        a2.isEnabled = true
        a3.isEnabled = true
        a4.isEnabled = true
        a5.isEnabled = true
        a6.isEnabled = true
        b1.isEnabled = true
        b2.isEnabled = true
        b3.isEnabled = true
        b4.isEnabled = true
        b5.isEnabled = true
        b6.isEnabled = true
        c1.isEnabled = true
        c2.isEnabled = true
        c3.isEnabled = true
        c4.isEnabled = true
        c5.isEnabled = true
        c6.isEnabled = true
        d1.isEnabled = true
        d2.isEnabled = true
        d3.isEnabled = true
        d4.isEnabled = true
        d5.isEnabled = true
        d6.isEnabled = true
        e1.isEnabled = true
        e2.isEnabled = true
        e3.isEnabled = true
        e4.isEnabled = true
        e5.isEnabled = true
        e6.isEnabled = true
        f1.isEnabled = true
        f2.isEnabled = true
        f3.isEnabled = true
        f4.isEnabled = true
        f5.isEnabled = true
        f6.isEnabled = true
    }

    private fun bookSeat()
    {
        val sharedPreferences = getSharedPreferences("own_pref", MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 0)
        val currentDate = Calendar.getInstance()
        val dateFormatDatabase = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val bookingDate = dateFormatDatabase.format(currentDate.time)
        val booking = Booking(
            UserId = id,
            MovieName = movieId.toString(),
            BookingDate = bookingDate,
            ShowDate = selectDate,
            Time = time,
            SeatNo = seats
        )
        Log.i("data", seats.toString())
        CoroutineScope(Dispatchers.IO).launch {
            val bookingService = BookingService()
            val response = bookingService.bookSeats(booking)
            if (response.code == HttpURLConnection.HTTP_OK)
            {
                withContext(Dispatchers.Main)
                {
                    Toast.makeText(this@BookedSeatsActivity, "Booking Done!", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this@BookedSeatsActivity, BookingActivity::class.java))
                    finish()
                }
            }
        }
    }

}