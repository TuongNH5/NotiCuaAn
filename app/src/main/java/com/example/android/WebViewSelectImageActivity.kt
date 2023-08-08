package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
class WebViewSelectImageActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    // File upload request code
    private val FILE_CHOOSER_RESULT_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_select_image)

        webView = findViewById(R.id.webViewTest)

        // Enable JavaScript in the WebView
        webView.settings.javaScriptEnabled = true

        // Set up the WebChromeClient to handle file selection
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                callback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                if (filePathCallback != null) {
                    filePathCallback!!.onReceiveValue(null)
                    filePathCallback = null
                }

                filePathCallback = callback // Store the callback to use it later
                // Create an intent to open the file picker
                val intent = fileChooserParams?.createIntent()
                startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE)
                return true
//                filePathCallback = callback // Store the callback to use it later
//                // Create an intent to open the file picker
//                val intent = fileChooserParams?.createIntent()
//                startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE)
//                return true
            }
        }

        // Load the web page in the WebView
        webView.loadUrl("file:///android_asset/index.html")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            // Get the selected file(s) from the intent and pass them to JavaScript
            if (resultCode == Activity.RESULT_OK && data != null) {
                val uris = if (data.data != null) {
                    arrayOf(data.data!!)
                } else {
                    data.clipData?.let { clipData ->
                        val uriList = mutableListOf<Uri>()
                        for (i in 0 until clipData.itemCount) {
                            uriList.add(clipData.getItemAt(i).uri)
                        }
                        uriList.toTypedArray()
                    }
                }
                filePathCallback?.onReceiveValue(uris)
                filePathCallback = null
                // Optionally, you can show a toast to indicate that the image(s) have been selected
                if (uris != null && uris.isNotEmpty()) {
                    Toast.makeText(this, "Images selected: ${uris.size}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If the user cancels the file picker, call onReceiveValue with null to cancel the request
                filePathCallback?.onReceiveValue(null)
                filePathCallback = null
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}