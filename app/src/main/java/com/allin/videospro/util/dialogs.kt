package com.allin.videospro.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import com.allin.videospro.R
import com.allin.videospro.databinding.DialogNetworkBinding

fun Activity.openNetworkDialog(isNetworkConnected:Boolean): AlertDialog {
    val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
    val binding = DialogNetworkBinding.inflate(layoutInflater)
    builder.setView(binding.root)
    binding.apply {
        retry.setOnClickListener {
            if (isNetworkConnected) builder.dismiss()
        }

        connectNow.setOnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
    builder.setCancelable(false)
    builder.show()

    return builder
}