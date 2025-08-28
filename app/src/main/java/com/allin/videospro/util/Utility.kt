package com.allin.videospro.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import java.io.File

object Utility {


    fun isWhatsappInstalled(): Boolean{
        val folder = File(
            "/storage/emulated/0/Android/media/com.whatsapp/WhatsApp"
        )
        if (folder.exists()) return true else return false
    }

    fun Activity.rateApp(){
        val packageName = packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }

    fun Activity.shareApp(){
        val appPackageName = packageName
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Check out this awesome app!")
            putExtra(
                Intent.EXTRA_TEXT,
                "Hey, check out this app: https://play.google.com/store/apps/details?id=$appPackageName"
            )
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

}