package com.example.ownticketbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ownticketbook.models.Booking
import com.example.ownticketbook.services.BookingService
import com.example.ownticketbook.utils.ApiRequest
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonActivity : AppCompatActivity()
{

    private lateinit var txtseats: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        val lblseatsnumber = findViewById<TextView>(R.id.lblseatsnumber)
        txtseats = findViewById(R.id.lblseatsnumber)

        val sharedPreferences1 = getSharedPreferences("own_pref", MODE_PRIVATE)
        val id = sharedPreferences1.getInt("id",0)
        val sharedPreferences = getSharedPreferences("own_pref$id", MODE_PRIVATE)
        val sets = sharedPreferences.getString("sets","No data")

        txtseats.text = sets

        findViewById<TextView>(R.id.btnbaketohome).let {
            it.setOnClickListener {
                val intent = Intent(this, MoviesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}