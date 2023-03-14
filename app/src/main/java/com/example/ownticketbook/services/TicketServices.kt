package com.example.ownticketbook.services

import android.media.Image
import com.example.ownticketbook.utils.ApiRequest
import com.example.ownticketbook.utils.ApiResponse
import com.google.gson.Gson

class TicketServices
{
    fun getAllSet(): ApiResponse
    {
        return ApiRequest.get(ApiRequest.TICKET_URL)
    }

    fun  getMovieImages(movieImage: Image): ApiResponse
    {
        return ApiRequest.post(ApiRequest.MOVIE_URL, Gson().toJson(movieImage))
    }

    fun getMovie(movieId: Int): ApiResponse
    {
        return ApiRequest.get(ApiRequest.MOVIE_URL.plus("?Id=").plus(movieId))
    }
}