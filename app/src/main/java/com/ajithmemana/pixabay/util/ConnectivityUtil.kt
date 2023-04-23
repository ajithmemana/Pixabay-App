package com.ajithmemana.pixabay.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.mutableStateOf

/**
 * Created by ajithmemana
 */
var isNetworkConnected = mutableStateOf(false)

val networkCallback = object : ConnectivityManager.NetworkCallback() {

    // network is available for use
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isNetworkConnected.value = true
    }

    // Network capabilities have changed for the network
    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities,
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        val unMetered =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        isNetworkConnected.value = unMetered
    }

    // lost network connection
    override fun onLost(network: Network) {
        super.onLost(network)
        isNetworkConnected.value = false
    }
}

