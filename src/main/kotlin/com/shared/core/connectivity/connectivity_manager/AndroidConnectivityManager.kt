package com.shared.core.connectivity.connectivity_manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.shared.core.connectivity.ConnectivityHelper
import com.shared.core.connectivity.internet_access_observer.InternetAccessObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidConnectivityManager(
    context: Context,
    private val internetAccessObserver: InternetAccessObserver,
) : ConnectivityHelper {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isNetworkConnected =
        MutableStateFlow(value = isCurrentlyConnected())

    override val isConnected: StateFlow<Boolean> =
        _isNetworkConnected.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            validateInternet()
        }

        override fun onLost(network: Network) {
            _isNetworkConnected.value = false
        }

        override fun onUnavailable() {
            _isNetworkConnected.value = false
        }
    }

    init {
        connectivityManager.registerNetworkCallback(buildNetworkRequest(), networkCallback)
        observeInternetValidation()
    }

    private fun observeInternetValidation() {
        internetAccessObserver.isInternetAvailable = { isAvailable ->
            _isNetworkConnected.value = isAvailable
        }
    }

    private fun validateInternet() {
        internetAccessObserver.getInternetAccessResponse()
    }

    private fun isCurrentlyConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun buildNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
    }

}