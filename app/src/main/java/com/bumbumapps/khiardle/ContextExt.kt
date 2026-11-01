package com.bumbumapps.khiardle

import android.app.Activity
import android.content.Context

fun Context.findActivity(): Activity? {
    var c: Context? = this
    while (c is android.content.ContextWrapper) {
        if (c is Activity) return c
        c = c.baseContext
    }
    return null
}
