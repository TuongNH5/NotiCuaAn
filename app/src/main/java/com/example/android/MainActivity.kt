package com.example.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assuming you want to start the SecondActivity when a button is clicked
        val button = findViewById<Button>(R.id.btnTest)
        button.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            // Start the WebViewActivity
            startActivity(intent)
        }


//        private fun initWebView2() {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
////                WebView.setWebContentsDebuggingEnabled(true)
////            }
////        }
//            webView.clearHistory()
//            webView.clearFormData()
//            webView.clearCache(true)
//            CookieManager.getInstance().removeAllCookie()
//            webView.webViewClient = WebViewClient()
//            webView.settings.javaScriptEnabled = true
//            webView.webViewClient = WebViewClient() // Use WebViewClient to handle page navigation within WebView
//            webView.loadUrl("http://172.16.0.142:8000")
////        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
////        android.webkit.WebView.setWebContentsDebuggingEnabled(true)
//            webView.settings.domStorageEnabled = true
////        webView.settings.allowFileAccess = true
////        webView.settings.allowContentAccess = true
////        webView.settings.allowFileAccessFromFileURLs = true
////        webView.settings.allowUniversalAccessFromFileURLs = true
////        webView.settings.javaScriptCanOpenWindowsAutomatically = true
////        webView.settings.mediaPlaybackRequiresUserGesture = false
////        webView.settings.setSupportMultipleWindows(true)
////        webView.settings.setSupportZoom(true)
////        webView.settings.builtInZoomControls = true
////        webView.settings.displayZoomControls = false
////        webView.settings.useWideViewPort = true
////        webView.settings.loadWithOverviewMode = true
////        webView.settings.loadsImagesAutomatically = true
////        webView.settings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
////        webView.settings.databaseEnabled = true
////        webView.settings.setGeolocationEnabled(true)
//            webView.webViewClient = object : WebViewClient() {
//                override fun onPageFinished(view: WebView?, url: String?) {
//                }
//            }
//            //file chooser
//            webView.webChromeClient = object : WebChromeClient() {
//                override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
//                    Log.d(
//                        "MyApplication webChromeClient", consoleMessage.message() + " -- From line "
//                                + consoleMessage.lineNumber() + " of "
//                                + consoleMessage.sourceId()
//                    )
//                    return true
//                }
//
//                override fun onShowFileChooser(
//                    webView: WebView?,
//                    callback: ValueCallback<Array<Uri>>?,
//                    fileChooserParams: FileChooserParams?
//                ): Boolean {
//                    //open image picker activi
//                    if (filePathCallback != null) {
//                        filePathCallback!!.onReceiveValue(null)
//                        filePathCallback = null
//                    }
//
//                    filePathCallback = callback // Store the callback to use it later
//                    // Create an intent to open the file picker
//                    val intent = fileChooserParams?.createIntent()
//                    startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE)
//                    return true
//                }
//
//            }
//            webView.addJavascriptInterface(
//                object : MyWebInterface {
//                    @JavascriptInterface
//                    override fun postMessage(message: String) {
//                        if (message != "") {
//                            val jsonObject = JSONObject(message)
//                            val actionValue = jsonObject.getString("action")
//                            println("message actionValue: $actionValue")
//                            println("message : $message")
//                            when (actionValue) {
//                                getInputHouseSDK -> sendInputToWebView()
//                                onBack -> clickOnBackWebView()
//                                else -> println("Nothing")
//                            }
//                        }
//                    }
//                },
//                "callbackToApp"
//            )
//
//        }

    }
}
