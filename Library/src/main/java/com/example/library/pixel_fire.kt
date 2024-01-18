//package com.example.library
//
////import android.content.Context
//class pixel_fire(private val context: Context) {
////    private val myClassInstance = Myclass(context)
//
//
//
//    fun printAllInfo() {
////        val packageName = myClassInstance.getPackageName()
////        val timezone = myClassInstance.getTimezone()
////        val advertisingId = myClassInstance.getAdvertisingId()
////        val platform = myClassInstance.getPlatform()
////        val connectionType = myClassInstance.getConnectionType()
//
////        println("Package Name: $packageName")
////        println("Timezone: $timezone")
////        println("Advertising ID: $advertisingId")
////        println("Platform: $platform")
//    }
//}
////package com.example.library
//
//import android.content.Context
//import android.content.pm.PackageManager
//import android.net.ConnectivityManager
//import android.net.NetworkCapabilities
//import android.os.Build
//import com.google.android.gms.ads.identifier.AdvertisingIdClient
//import java.util.TimeZone
//
////class Myclass(private val context: Context) {
////
//////    fun getPackageName(): String {
//////        return context.packageName
//////    }
////
//////    fun getTimezone(): String {
//////        return TimeZone.getDefault().id
//////    }
////
//////    fun getAdvertisingId(): String {
//////        val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
//////        return adInfo?.id ?: "Not available"
//////    }
////
////    fun getPlatform(): String {
////        return "Android"
////    }
////
//////    fun getConnectionType(): String {
//////        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//////        val network = connectivityManager.activeNetwork ?: return "No network available"
//////        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return "No network available"
//////        return when {
//////            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
//////            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
//////            else -> "Unknown"
//////        }
//////    }
////}