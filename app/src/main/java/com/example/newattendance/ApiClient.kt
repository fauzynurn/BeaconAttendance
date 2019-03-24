package com.example.newattendance

import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory;



class ApiClient {

    companion object {

        var retrofit: Retrofit? = null

        val BASE_URL = "http://192.168.100.8:8080/"

        fun getInstance(): Retrofit? {

            if (retrofit == null) {

                retrofit = retrofit2.Retrofit.Builder()

                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }

            return retrofit

        }

    }

}