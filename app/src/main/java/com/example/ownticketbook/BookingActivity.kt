package com.example.ownticketbook

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ownticketbook.adapters.MoviesAdapter
import com.example.ownticketbook.models.Booking
import com.example.ownticketbook.models.Movie
import com.example.ownticketbook.models.TotalSeats
import com.example.ownticketbook.services.BookingService
import com.example.ownticketbook.services.MoviesService
import com.example.ownticketbook.services.TicketServices
import com.example.ownticketbook.utils.ApiRequest
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.cache2.Relay.Companion.edit
import java.net.HttpURLConnection
import java.util.Date

class BookingActivity : AppCompatActivity()
{
    private lateinit var TicketServices: TicketServices
    private lateinit var recmovielistx: RecyclerView
    private lateinit var movieList: ArrayList<Movie>
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var lblmoviename: TextView
    private lateinit var lbldate: TextView
    private lateinit var lbltime: TextView
    private lateinit var image: ImageView
    private lateinit var txtprice: TextView
    private lateinit var txtseats: TextView
    private lateinit var txttotal: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)




        lblmoviename = findViewById(R.id.lblmoviename)
        lbldate = findViewById(R.id.lbldate)
        lbltime = findViewById(R.id.lbltime)
        image = findViewById(R.id.image)
        val lblseatsnumber = findViewById<TextView>(R.id.lblseatsnumber)

        txtprice = findViewById(R.id.txtprice)
        txtseats = findViewById(R.id.txtseats)
        txttotal = findViewById(R.id.txttotal)

        val id = intent.getIntExtra("id",0)
        val seats = intent.getStringArrayListExtra("seats")

        CoroutineScope(Dispatchers.IO).launch {
            val bookingService = BookingService()
            val response = bookingService.getBooking(id)
            val bookings = Gson().fromJson(response.message, Array<Booking>::class.java)

            for (booking in bookings)
            {
                val ImageName = "${ApiRequest.IMAGE_URL}/${booking.ImageName}"
                withContext(Dispatchers.Main)
                {
                    Glide.with(this@BookingActivity).load(ImageName).into(image)
                    lblmoviename.text = booking.MovieName
                    lbldate.text = booking.ShowDate
                    lbltime.text = booking.Time
                    txtprice.text = booking.TicketPrice
                    txtseats.text = booking.TotalSeats
                    txttotal.text = booking.Total
                    lblseatsnumber.text = seats.toString()
                }
            }
        }


        findViewById<TextView>(R.id.btnbaketohome).let {
            it.setOnClickListener {
                val intent = Intent(this, MoviesActivity::class.java)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.btnuser).let{
            it.setOnClickListener {
                val sharedPreferences1 = getSharedPreferences("own_pref", MODE_PRIVATE)
                val id = sharedPreferences1.getInt("id",0)
                val sharedPreferences = getSharedPreferences("own_pref$id", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("sets",lblseatsnumber.text.toString())
                editor.apply()
                val intent = Intent(this, PersonActivity::class.java)
                startActivity(intent)
            }
        }


    }
}