package com.example.android

import android.content.Context
import android.os.Build
import android.webkit.JavascriptInterface
import android.widget.Toast


class WebAppInterface // Instantiate the interface and set the context
internal constructor(var mContext: Context) {
    // Show a toast from the web page
    @JavascriptInterface
    fun showToast(toast: String?) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }

    @get:JavascriptInterface
    val androidVersion: Int
        get() = Build.VERSION.SDK_INT

    @JavascriptInterface
    fun showAndroidVersion(versionName: String?) {
        Toast.makeText(mContext, versionName, Toast.LENGTH_SHORT).show()
    }
}