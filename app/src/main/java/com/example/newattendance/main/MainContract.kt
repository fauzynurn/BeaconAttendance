package com.example.newattendance.main

interface MainContract {
    fun onAttendanceStored(session : Int)
    fun onError(message : String)
}