package com.drsync.ghilblianime.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

object Constants {
    const val BASE_URL = "https://ghibliapi.herokuapp.com/"
    const val TAG = "response::::::"
    const val DATA_TAG = "data"
    const val POSITION_TAG = "position"

    fun Context.createProgress(): CircularProgressDrawable {
        return CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
    }

}