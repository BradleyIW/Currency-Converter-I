package com.bradley.wilson.core.connectivity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import com.bradley.wilson.R
import com.bradley.wilson.currency.feed.ConnectivityListener
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class ActivityLifecycleConnectivityCallback : Application.ActivityLifecycleCallbacks {
    private lateinit var connectionSnackbar: Snackbar

    private val connectivityListener: ConnectivityListener =
        ConnectivityListener(networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                showConnectivitySnackbar()
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                showConnectivitySnackbar()
            }

            @SuppressLint("InlinedApi")
            override fun onAvailable(network: Network) {
                hideConnectivitySnackbar()
            }
        })

    private fun hideConnectivitySnackbar() {
        connectionSnackbar.dismiss()
    }

    private fun showConnectivitySnackbar() {
        connectionSnackbar.show()
    }

    override fun onActivityPaused(activity: Activity) {
        //Do nothing
    }

    override fun onActivityStarted(activity: Activity) =
        connectivityListener.registerListener(activity)

    override fun onActivityDestroyed(activity: Activity) {
        //Do nothing
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        //Do nothing
    }

    override fun onActivityStopped(activity: Activity) =
        connectivityListener.unregisterListener(activity)

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activity.apply {
            connectionSnackbar = Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.no_connection_error_message),
                Snackbar.LENGTH_INDEFINITE
            )
            connectionSnackbar.setTextColor(resources.getColor(R.color.black))
            val view = connectionSnackbar.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            connectionSnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
            showConnectivitySnackbar()
        }
    }

    override fun onActivityResumed(activity: Activity) {
        //Do nothing
    }
}
