package com.example.ownticketbook.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ownticketbook.models.Movie
import com.example.ownticketbook.R

class MoviesAdapter(
    private var context: Context,
    private var Movie: ArrayList<Movie>,
    private var clickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.activity_movie_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(Movie[position])
    }

    override fun getItemCount(): Int
    {
        return Movie.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private var image: ImageView = itemView.findViewById(R.id.image)
        private var title: TextView = itemView.findViewById(R.id.txttitle)

        private var description: TextView = itemView.findViewById(R.id.txtdescription)
        private var releasedate: TextView = itemView.findViewById(R.id.txtreleasedate)
        private var language: TextView = itemView.findViewById(R.id.txtlanguage)
        private var price: TextView = itemView.findViewById(R.id.txtprice)
        private var btnBook: Button = itemView.findViewById(R.id.btnbooknow)
        private var firstshowtime: TextView = itemView.findViewById(R.id.txtfirstshowtime)
        private var secondshowtime: TextView = itemView.findViewById(R.id.txtsecondshowtime)

        fun bind(movie: Movie)
        {
            Glide.with(context).load(movie.ImageName).into(image)

            title.text = movie.Name
            description.text = movie.Description
            releasedate.text = movie.ReleaseDate
            language.text = movie.Language
            price.text = movie.TicketPrice.toString()
            firstshowtime.text = movie.FirstShowTime
            secondshowtime.text = movie.SecondShowTime

            btnBook.setOnClickListener { clickListener?.onClick(movie) }
        }
    }

    interface OnItemClickListener
    {
        fun onClick(movie: Movie)
    }

}