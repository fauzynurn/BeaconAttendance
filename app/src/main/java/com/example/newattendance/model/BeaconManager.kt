package com.example.newattendance.model

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.util.Log
import android.widget.Toast
import com.example.newattendance.interfaces.IAttendanceCallback
import com.eyro.cubeacon.*
import java.util.*

class BeaconManager(private val callback : IAttendanceCallback, private val context : Context) : CBServiceListener{
    private lateinit var cubeacon : Cubeacon
    private lateinit var region : CBRegion
    private var session : Int = 0
    override fun getApplicationContext(): Context {
        return context
    }

    override fun unbindService(p0: ServiceConnection?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindService(p0: Intent?, p1: ServiceConnection?, p2: Int): Boolean {
        return false
    }

    override fun onBeaconServiceConnect() {
        try {
            Log.i("MASUK", "HAHAHA")
            cubeacon.addRangingListener { p0, _ ->
                if (p0.size > 0 && p0?.any { it.macAddress == "C2:00:E2:00:00:6A" }!!) {
                    Log.i("TEST", p0.first()?.macAddress)
                    session++
                    callback.onResult(session)
                }
                if (session > 4) {
                    cubeacon.stopRangingBeaconsInRegion(region)
                }
            }
            cubeacon.startRangingBeaconsInRegion(region)
        } catch (e: Exception) {
            callback.onFail(e.toString())
        }
    }

    fun startBeaconScanning(){
        Logger.setLogLevel(LogLevel.VERBOSE)
        Cubeacon.initialize(context)
        cubeacon = Cubeacon.getInstance()
        cubeacon.connect(this)
        region = CBRegion(
            "regiontest",UUID.fromString("CB10023F-A318-3394-4199-A8730C7C1AEC"),222,206
        )
    }

}