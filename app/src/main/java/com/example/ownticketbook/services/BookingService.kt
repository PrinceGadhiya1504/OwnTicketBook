package com.example.ownticketbook.services

import com.example.ownticketbook.utils.ApiRequest
import com.example.ownticketbook.utils.ApiResponse

class BookingService
{
    fun getBookedSeats(time: String): ApiResponse
    {
        return ApiRequest.get(ApiRequest.SEAT_URL.plus("?time=$time"))
    }
}