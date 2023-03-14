package com.example.ownticketbook.services

import com.example.ownticketbook.models.Booking
import com.example.ownticketbook.utils.ApiRequest
import com.example.ownticketbook.utils.ApiResponse
import com.google.gson.Gson

class BookingService
{
    fun getBookedSeats(time: String, date: String): ApiResponse
    {
        return ApiRequest.get(ApiRequest.SEAT_URL.plus("?time=$time&date=$date"))
    }

    fun bookSeats(booking: Booking): ApiResponse
    {
        return ApiRequest.post(ApiRequest.SEAT_URL,Gson().toJson(booking))
    }

    fun getBooking(id: Int): ApiResponse
    {
        return ApiRequest.get(ApiRequest.TICKET_URL.plus("?id=$id"))
    }
}