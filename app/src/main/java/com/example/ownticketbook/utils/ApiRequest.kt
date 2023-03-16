package com.example.ownticketbook.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ApiRequest
{
    companion object
    {
        @JvmStatic val BASE_URL = "http://172.28.0.4/MovieTicket"
        @JvmStatic val IMAGE_URL = "$BASE_URL/admin/images"
        @JvmStatic val API_URL = "$BASE_URL/api"
        @JvmStatic val LOGIN_URL = "$API_URL/login.php"
        @JvmStatic val REGISTER_URL = "$API_URL/register.php"
        @JvmStatic val USERS_URL = "$API_URL/users.php"
        @JvmStatic val MOVIE_URL = "$API_URL/movie.php"
        @JvmStatic val SEAT_URL = "$API_URL/bookedseats.php"
        @JvmStatic val TICKET_URL = "$API_URL/ticket.php"

        @JvmStatic
        fun get(url: String): ApiResponse
        {
            return send(Request.Builder().url(url).build())
        }

        @JvmStatic
        fun post(url: String, body: String): ApiResponse
        {
            return send(Request.Builder().url(url).post(body.toRequestBody()).build())
        }

        @JvmStatic
        fun put(url: String, body: String): ApiResponse
        {
            return send(Request.Builder().url(url).put(body.toRequestBody()).build())
        }

        @JvmStatic
        fun delete(url: String): ApiResponse
        {
            return send(Request.Builder().url(url).delete().build())
        }

        @JvmStatic
        private fun send(request: Request): ApiResponse {
            val client = OkHttpClient()
            val response = client.newCall(request).execute()

            return ApiResponse(
                code = response.code,
                message = response.body!!.string()
            )
        }
    }
}