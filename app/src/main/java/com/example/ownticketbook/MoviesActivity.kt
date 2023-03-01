package com.example.ownticketbook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ownticketbook.adapters.MoviesAdapter
import com.example.ownticketbook.models.Movie
import com.example.ownticketbook.services.MoviesService
import com.example.ownticketbook.utils.ApiRequest
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection

class MoviesActivity : AppCompatActivity()
{
    private lateinit var moviesService: MoviesService
    private lateinit var recmovielist: RecyclerView
    private lateinit var movieList: ArrayList<Movie>
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        recmovielist = findViewById(R.id.recmovielist)
        configureData()
    }

    private fun configureData()
    {
        CoroutineScope(Dispatchers.IO).launch {
            moviesService = MoviesService()
            val response = moviesService.getAllMovie()

            if (response.code == HttpURLConnection.HTTP_NOT_FOUND)
            {
                Toast.makeText(this@MoviesActivity, "No Data Found", Toast.LENGTH_LONG).show()
                return@launch
            }

            movieList = ArrayList()
            val movies = Gson().fromJson(response.message, Array<Movie>::class.java)

            for (movie in movies)
            {
                movieList.add(
                    Movie(
                        Id = movie.Id,
                        Name = movie.Name,
                        ReleaseDate = movie.ReleaseDate,
                        Description = movie.Description,
                        Language = movie.Language,
                        TicketPrice = movie.TicketPrice,
                        ImageName = "${ApiRequest.IMAGE_URL}/${movie.ImageName}",
                        FirstShowTime = movie.FirstShowTime,
                        SecondShowTime = movie.SecondShowTime
                    )
                )
            }

            withContext(Dispatchers.Main) {
                moviesAdapter = MoviesAdapter(
                    this@MoviesActivity,
                    movieList,
                    object : MoviesAdapter.OnItemClickListener
                    {
                        override fun onClick(movie: Movie)
                        {
                            val intent = Intent(this@MoviesActivity, BookedSeatsActivity::class.java)
                            intent.putExtra("Id", movie.Id)
                            intent.putExtra("Name", movie.Name)
                            intent.putExtra("FirstShowTime",movie.FirstShowTime)
                            intent.putExtra("SecondShowTime",movie.SecondShowTime)
                            startActivity(intent)
                        }
                    })

                recmovielist.layoutManager = GridLayoutManager(this@MoviesActivity, 1)
                recmovielist.adapter = moviesAdapter
            }
        }
    }
}
