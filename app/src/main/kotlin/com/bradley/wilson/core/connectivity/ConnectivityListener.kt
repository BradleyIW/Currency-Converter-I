@file:Suppress("MissingPermission")

package com.bradley.wilson.core.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.bradley.wilson.core.compatibility.Compatibility

class ConnectivityListener(
    private val compatibility: Compatibility = Compatibility(),
    private val networkCallback: ConnectivityManager.NetworkCallback
) {
    fun registerListener(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (compatibility.supportsAndroidVersion(Build.VERSION_CODES.N)) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }

    fun unregisterListener(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
