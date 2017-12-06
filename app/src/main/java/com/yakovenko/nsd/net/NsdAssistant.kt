package com.yakovenko.nsd.net

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Handler
import android.os.Looper
import javax.inject.Inject

class NsdAssistant @Inject constructor(private val manager: NsdManager) {

    interface Listener {
        fun onServiceRegistered()
        fun onServiceUnregistered()

        fun onServiceFound(info: NsdServiceInfo)
        fun onServiceLost(info: NsdServiceInfo)
    }

    companion object {
        const val SERVICE_NAME = "SERVICE_NAME"
        const val SERVICE_TYPE = "_http._tcp"
    }

    val isDiscovering: Boolean get() = discoveryListener != null
    val isServiceCreated: Boolean get() = registrationListener != null

    var listener: Listener? = null

    private var registrationListener: NsdManager.RegistrationListener? = null
    private var discoveryListener: NsdManager.DiscoveryListener? = null

    private val handler = Handler(Looper.getMainLooper())

    var current: NsdServiceInfo? = null

    fun create(port: Int, serviceName: String = SERVICE_NAME) {
        registrationListener ?: initializeRegistrationListener().also { registrationListener = it }

        manager.registerService(NsdServiceInfo().apply {
            this.port = port
            this.serviceName = serviceName
            serviceType = SERVICE_TYPE

        }, NsdManager.PROTOCOL_DNS_SD, registrationListener)
    }

    fun discover() = manager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD,
            discoveryListener?.let { return } ?: initializeDiscoveryListener().also {
                discoveryListener = it
            })

    fun release() {
        listener = null
        stopDiscovering()
        stopService()
    }

    fun stopDiscovering() {
        discoveryListener?.let {
            manager.stopServiceDiscovery(it)
            discoveryListener = null
        }
    }

    fun stopService() {
        registrationListener?.let {
            manager.unregisterService(it)
            registrationListener = null
        }
    }

    //region NsdManager's listeners
    private fun initializeRegistrationListener() = object : NsdManager.RegistrationListener {
        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
            manager.unregisterService(this).also { registrationListener = null }
        }

        override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
            registrationListener = null
            current = null
            handler.post { listener?.onServiceUnregistered() }
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
            manager.unregisterService(this).also { registrationListener = null }
        }

        override fun onServiceRegistered(serviceInfo: NsdServiceInfo) {
            current = serviceInfo
            handler.post { listener?.onServiceRegistered() }
        }
    }

    private fun initializeDiscoveryListener() = object : NsdManager.DiscoveryListener {
        override fun onServiceFound(serviceInfo: NsdServiceInfo) {
            if (serviceInfo.serviceName != current?.serviceName) {
                handler.post { listener?.onServiceFound(serviceInfo) }
            }
        }

        override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
            manager.stopServiceDiscovery(this).also { discoveryListener = null }
        }

        override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
            manager.stopServiceDiscovery(this).also { discoveryListener = null }
        }

        override fun onServiceLost(serviceInfo: NsdServiceInfo) {
            handler.post { listener?.onServiceLost(serviceInfo) }
        }

        override fun onDiscoveryStarted(serviceType: String?) = Unit
        override fun onDiscoveryStopped(serviceType: String?) = Unit
    }
    //endregion
}