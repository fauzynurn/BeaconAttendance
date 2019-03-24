package com.example.newattendance

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {
    @GET("getAllCollege")
    fun getAllCollege(): Call<String>

    @POST("attendancesign")
    fun sign(@Body sign : Sign) : Call<String>

    @GET("getPublicKey")
    fun getPublicKey() : Call<PublicKey>
}