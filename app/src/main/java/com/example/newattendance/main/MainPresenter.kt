package com.example.newattendance.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import com.example.newattendance.interfaces.IAttendanceCallback
import com.example.newattendance.model.BeaconManager
import com.eyro.cubeacon.CBServiceListener
import java.util.*

class MainPresenter(private val contract: MainContract, private val context : Context) : BroadcastReceiver(){
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var calendar : Calendar
    private lateinit var beaconManager: BeaconManager
    override fun onReceive(context: Context?, intent: Intent?) {
        beaconManager.startBeaconScanning()
        val calendar: Calendar = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("Asia/Jakarta")
            timeInMillis = System.currentTimeMillis() + 3000000
        }
        alarmMgr?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent)
    }

    fun assignAttendance(){
            beaconManager = BeaconManager(object : IAttendanceCallback{
            override fun onResult(session: Int) {
                contract.onAttendanceStored(session)
            }

            override fun onFail(message: String) {
                contract.onError(message)
            }

        }, context)
        beaconManager.startBeaconScanning()
//        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmIntent = Intent(context, MainPresenter::class.java).let { intent ->
//            PendingIntent.getBroadcast(context, 0, intent, 0)
//        }

//        calendar = Calendar.getInstance().apply {
//            timeZone = TimeZone.getTimeZone("Asia/Jakarta")
//            timeInMillis = System.currentTimeMillis() + 120000
//        }
//
//        alarmMgr?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            alarmIntent)
    }
}