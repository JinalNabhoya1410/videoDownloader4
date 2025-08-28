package com.allin.videospro.ui.base

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.allin.videospro.ui.activity.common.LanguageActivity
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.TinyDB
import com.allin.videospro.util.openNetworkDialog
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
    private var currentLanguage: String = ""
    private var networkDialog: AlertDialog? = null
    private var connectivityManager: ConnectivityManager? = null
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        currentLanguage = TinyDB(this).getString(AppConstance.SELECTED_LANGUAGE, "en")
        setLocale(this, currentLanguage)
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        registerNetworkCallback()
    }

    override fun onResume() {
        super.onResume()
        val lang = TinyDB(this).getString(AppConstance.SELECTED_LANGUAGE, "en")
        if (lang != currentLanguage && this !is LanguageActivity) {
            currentLanguage = lang
            recreate()
        }

        if (!isNetworkConnected()) {
            if (networkDialog == null || !networkDialog!!.isShowing) {
                networkDialog = openNetworkDialog(isNetworkConnected())
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val tinyDB = TinyDB(newBase!!)
        val langCode = tinyDB.getString(AppConstance.SELECTED_LANGUAGE, "en")
        val context = setLocale(newBase, langCode)
        super.attachBaseContext(context)
    }


    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return context.createConfigurationContext(config)
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun registerNetworkCallback() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    networkDialog?.let {
                        if (it.isShowing) it.dismiss()
                    }
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    if (networkDialog == null || !networkDialog!!.isShowing) {
                        networkDialog = openNetworkDialog(isNetworkConnected())
                    }
                }
            }
        }

        connectivityManager?.registerNetworkCallback(request, networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
}