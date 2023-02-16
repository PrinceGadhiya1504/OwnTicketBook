package com.example.ownticketbook.services

import android.media.Image
import com.example.ownticketbook.utils.ApiRequest
import com.example.ownticketbook.utils.ApiResponse
import com.google.gson.Gson

class MoviesService
{
    fun getAllMovie(): ApiResponse
    {
        return ApiRequest.get(ApiRequest.MOVIE_URL)
    }

    fun  getMovieImages(movieImage: Image): ApiResponse
    {
        return ApiRequest.post(ApiRequest.MOVIE_URL, Gson().toJson(movieImage))
    }
}