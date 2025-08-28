package com.allin.videospro.util

import android.view.View

fun View.beVisible(){
    visibility = View.VISIBLE
}

fun View.beGone(){
    visibility = View.GONE
}

fun View.beInvisible(){
    visibility = View.INVISIBLE
}