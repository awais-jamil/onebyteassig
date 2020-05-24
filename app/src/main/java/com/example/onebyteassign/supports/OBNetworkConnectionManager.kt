package com.example.onebyteassign.supports

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class OBNetworkConnectionManager {
    companion object {

        val isConnected: Boolean
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            get() {
                (OBApplication.applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
                    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getNetworkCapabilities(activeNetwork)?.run {
                                when {
                                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                                    else -> false
                                }
                            } ?: false
                        } else {
                            TODO("VERSION.SDK_INT < M")
                        }
                    } else {
                        TODO("VERSION.SDK_INT < LOLLIPOP")
                    }
                }
            }
    }
}