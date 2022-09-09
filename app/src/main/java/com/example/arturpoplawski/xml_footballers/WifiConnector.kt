package com.example.arturpoplawski.xml_footballers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.util.Log

private const val LOGGER_TAG = "WifiConnector"

class WifiConnector(
    private val context: Context,
//    private val networkConnectivityObserver: NetworkConnectivityObserver,
    private val logger: Logger
) {

    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    sealed class Result {
        object Success : Result()
        object Failure : Result()
    }

    fun switchOnWifi() {
        wifiManager.setWifiEnabled(true)
    }

    // using deprecated classes since we are using android 9 only atm
    fun connect(networkSSID: String, networkPassword: String): Boolean {
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        context.registerReceiver(NetworkStateObserver(), intentFilter)


        val config = WifiConfiguration().apply {
            SSID = String.format("\"%s\"", networkSSID)
            preSharedKey = String.format("\"%s\"", networkPassword)
            allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE) // for open wifis
        }
        val networkId = wifiManager.addNetwork(config)

        wifiManager.disconnect()

        logger.log(LOGGER_TAG) { "Logging to ${config.SSID} with password ${config.preSharedKey}" }

        val networkEnabledSuccessfully = wifiManager.enableNetwork(networkId, true)
        val reconnectedSuccessfully = wifiManager.reconnect()

        logger.log(LOGGER_TAG) { "Network enabled successfully: $networkEnabledSuccessfully, reconnected successfully: $reconnectedSuccessfully" }

        return networkEnabledSuccessfully && reconnectedSuccessfully
    }
}

private const val LOGGER_TAG_2 = "NetworkStateObserver"

class NetworkStateObserver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            val info: NetworkInfo? = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO)

            Log.d(LOGGER_TAG_2, "state: ${info?.state}, ${info?.extraInfo}, ${info?.detailedState}")
        }
    }
}

class Logger {

    fun log(tag: String, message: () -> String) {
        Log.d(tag, message.invoke())
    }
}