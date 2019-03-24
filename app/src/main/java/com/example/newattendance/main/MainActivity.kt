package com.example.newattendance.main

import android.os.Bundle
import android.os.RemoteException
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.eyro.cubeacon.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), MainContract, CBServiceListener {
    private lateinit var cubeacon: Cubeacon
    private lateinit var region: CBRegion
    override fun onBeaconServiceConnect() {
        cubeacon.addRangingListener { p0, p1 ->
            if (p0?.size!! > 0) {
                runOnUiThread {
                    attendance.text = p0.first().macAddress
                }
            }
        }
        try {
            // create a new region for ranging beacons
            val region = CBRegion(
                "regiontest",
                UUID.fromString("cb10023f-a318-3394-4199-a8730c7c1aec")
            )
            // start ranging beacons using region
            cubeacon.startRangingBeaconsInRegion(region)
        } catch (e: RemoteException) {
            Log.e("ERROR", "Error while start ranging beacon, $e")
        }

    }

    private lateinit var presenter: MainPresenter
    override fun onAttendanceStored(session: Int) {
        attendance.text = attendance.text.toString().plus("recorded on session $session,")
    }

    override fun onError(message: String) {
        error.text = message
    }

    private fun showNotification(title: String, message: String) {
        var mBuilder = NotificationCompat.Builder(applicationContext, "com.example.fauzy.attendance")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, mBuilder.build())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Cubeacon.initialize(this)
        cubeacon = Cubeacon.getInstance()

        if (SystemRequirementManager.checkAllRequirementUsingDefaultDialog(this)) {
            cubeacon.connect(this)
//            presenter = MainPresenter(this, applicationContext)
//            presenter.assignAttendance()
        }
//        try {
//
//            // 1. generate secret key using AES
//            val keyGenerator = KeyGenerator.getInstance("AES")
//            keyGenerator.init(128) // AES is currently available in three key sizes: 128, 192 and 256 bits.The design and strength of all key lengths of the AES algorithm are sufficient to protect classified information up to the SECRET level
//            val secretKey = keyGenerator.generateKey()
//
//            // 2. get string which needs to be encrypted
//            val text = "161511049"
//
//            // 3. encrypt string using secret key
//            val raw = secretKey.encoded
//            val skeySpec = SecretKeySpec(raw, "AES")
//            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, IvParameterSpec(ByteArray(16)))
//            val cipherTextString = Base64.encodeToString(cipher.doFinal(text.toByteArray(Charset.forName("UTF-8"))), Base64.DEFAULT)
//
//            val apiService = ApiClient.getInstance()?.create(ApiInterface::class.java)
//            call = apiService!!.getPublicKey()
//            call.enqueue(object : Callback<PublicKey> {
//            override fun onResponse(call: Call<PublicKey>, response: Response<PublicKey>) {
//                publicKey = response.body()!!.publicKey
//                // 4. get public key
//                val publicSpec = X509EncodedKeySpec(Base64.decode(publicKey, Base64.DEFAULT))
//                val keyFactory = KeyFactory.getInstance("RSA")
//                val publicKey = keyFactory.generatePublic(publicSpec)
//
//                // 6. encrypt secret key using public key
//                val cipher2 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
//                cipher2.init(Cipher.ENCRYPT_MODE, publicKey)
//                eSecretKey = Base64.encodeToString(cipher2.doFinal(secretKey.encoded), Base64.DEFAULT)
//                val sign = Sign().apply {
//                    this.encryptedSecretKey = eSecretKey
//                    this.cipherTextString = cipherTextString
//                }
//                val otherCall : Call<String> = apiService.sign(sign)
//                otherCall.enqueue(object : Callback<String> {
//                    override fun onResponse(call: Call<String>, response: Response<String>) {
//                        status.text = response.body()
//                    }
//                    override fun onFailure(call: Call<String>, t: Throwable) {
//                        Toast.makeText(applicationContext,"FAIL ${t.toString()}", Toast.LENGTH_LONG).show()
//                        Log.i("NESTEDERROR",t.message)
//                    }
//                })
//            }
//            override fun onFailure(call: Call<PublicKey>, t: Throwable) {
//                Toast.makeText(applicationContext,"FAIL ${t.toString()}", Toast.LENGTH_LONG).show()
//                Log.i("ERROR",t.message)
//            }
//        })

        // 7. pass cipherTextString (encypted sensitive data) and encryptedSecretKey to your server via your preferred way.
        // Tips:
        // You may use JSON to combine both the strings under 1 object.
        // You may use a volley call to send this data to your server.

//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        cubeacon.addMonitoringListener(this)
//        val apiService = ApiClient.getInstance()?.create(ApiInterface::class.java)
//        call = apiService!!.getAllCollege()
//        call.enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val jadwal = response.body()
//            }
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(applicationContext,"FAIL ${t.toString()}", Toast.LENGTH_LONG).show()
//            }
//        })
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                "com.example.fauzy.attendance",
//                "Attendance",
//                NotificationManager.IMPORTANCE_DEFAULT
//            ).apply {
//                description = "description"
//            }
//            var notificationManager: NotificationManager =
//                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//        viewAdapter = BeaconAdapter(list,this)
//        list_beacon.layoutManager = LinearLayoutManager(this)
//        list_beacon.adapter = viewAdapter
    }
}
