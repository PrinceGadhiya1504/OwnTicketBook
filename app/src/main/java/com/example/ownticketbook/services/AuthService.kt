package com.example.ownticketbook.services

import com.example.ownticketbook.models.User
import com.example.ownticketbook.utils.ApiRequest
import com.example.ownticketbook.utils.ApiResponse
import com.google.gson.Gson

class AuthService
{
    fun login(user: User): ApiResponse
    {
        return ApiRequest.post(ApiRequest.LOGIN_URL, Gson().toJson(user))
    }

    fun register(user: User): ApiResponse
    {
        return ApiRequest.post(ApiRequest.REGISTER_URL, Gson().toJson(user))
    }
}