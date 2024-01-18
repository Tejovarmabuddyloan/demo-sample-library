package com.example.library

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.util.TimeZone
import android.provider.Settings

import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.android.volley.toolbox.StringRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Myclass(val context: Context) {
    fun sayHello(): String {
        return "Hello from Plugin"
    }
    fun getTimeZone(): String {
        return TimeZone.getDefault().id
    }
    fun getAdvertisingId(callback: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
            val adId = adInfo?.id
            withContext(Dispatchers.Main) {
                callback(adId)
            }

        }
    }
    fun getPackageName(): String {
        return context.packageName
    }
    fun getPlatform(): String {
        return "Android"
    }
    fun getDate(): String {
        val current = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(current)
    }

    fun getTime(): String {
        val current = Date()
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return formatter.format(current)
    }

    data class IpResponse(val status: String, val message: String, val data: IpData)
    data class IpData(val ip: String)

    fun getIpAddress(context: Context, callback: (String) -> Unit) {
        val apiUrl = "https://utils.follow.whistle.mobi/ip_utils_loop.php"

        val request = JsonObjectRequest(Request.Method.GET, apiUrl, null,
            Response.Listener { response ->
                // Use Gson to parse JSON
                val ipResponse = Gson().fromJson(response.toString(), IpResponse::class.java)
                callback.invoke(ipResponse.data.ip)
            },
            Response.ErrorListener { error ->
                println("Error: Unable to fetch IP address. ${error.message}")
                callback.invoke("Unknown")
            })

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        // Add the request to the RequestQueue.
        queue.add(request)
    }
    fun getDeviceModel(): String {
        return Build.MODEL
    }
    fun getNetworkType(): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return "No active network"
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return "Unknown"

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
            else -> "Unknown"
        }
    }
    fun whistleLoopEvents(eventName: String, extraParameters: String = "") {
        getAdvertisingId { deviceId ->
            val packageName = "com.example.staging_loop_example"
            val networkType = getNetworkType()
            getIpAddress(context) { ipAddress ->
                val userAgent = getDeviceModel()
                val clickedTime = getTime()
                val clickedDate = getDate()
                val platformOs = getPlatform()
                val timeZone = getTimeZone()
                val token = "FcxbwOmR5MgutSv810zNDjaIP9LJChTX" // Replace with your token

                val url = "https://utils.follow.whistle.mobi/events_sdk_app.php?" +
                        "device_id=$deviceId&" +
                        "event_name=$eventName&" +
                        "package_name=$packageName&" +
                        "network_type=$networkType&" +
                        "ip=$ipAddress&" +
                        "user_agent=$userAgent&" +
                        "clicked_time=$clickedTime&" +
                        "clicked_date=$clickedDate&" +
                        "platform_os=$platformOs&" +
                        "time_zone=$timeZone&" +
                        "extraparams=$extraParameters&" +
                        "token=$token"

                println("POST $url")

                val stringRequest = StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        println("Response is: $response")
                    },
                    Response.ErrorListener { error ->
                        println("That didn't work! Error: $error")
                    })

                // Add the request to the RequestQueue.
                val queue = Volley.newRequestQueue(context)
                queue.add(stringRequest)
            }
        }
    }
}