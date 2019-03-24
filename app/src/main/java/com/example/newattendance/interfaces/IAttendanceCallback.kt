package com.example.newattendance.interfaces

interface IAttendanceCallback{
    fun onResult(session : Int)
    fun onFail(message : String)
}