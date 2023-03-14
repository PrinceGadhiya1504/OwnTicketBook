package com.example.ownticketbook.models

import java.util.Dictionary

data class Booking(
    val Id: Int = 0,
    val UserId: String = "",
    val MovieName: String = "",
    val ImageName: String = "",
    val BookingDate: String = "",
    val ShowDate: String = "",
    val Time: String = "",
    val SeatNo: ArrayList<String>,
    val TicketPrice: String = "",
    val TotalSeats: String = "",
    val Total: String = "",
    val IsPaid: String = ""
)
