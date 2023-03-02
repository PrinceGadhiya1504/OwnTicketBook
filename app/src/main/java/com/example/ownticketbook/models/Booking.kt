package com.example.ownticketbook.models

import java.util.Dictionary

data class Booking(
    val Id: Int = 0,
    val UserId: Int = 0,
    val MovieName: String = "",
    val BookingDate: String = "",
    val ShowDate: String = "",
    val Time: String = "",
    val SeatNo: Array<String>,
    val TicketPrice: String = "",
    val TotalSeats: String = "",
    val Total: String = ""
)
