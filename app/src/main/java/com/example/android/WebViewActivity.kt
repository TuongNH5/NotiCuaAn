package com.example.android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.json.JSONObject

class WebViewActivity : AppCompatActivity() {
    private var filePathCallback: ValueCallback<Array<android.net.Uri>>? = null
    private val FILE_CHOOSER_RESULT_CODE = 1
    private  val  getInputHouseSDK = "getInputHouseSDK"
    private  val  onBack = "onBack"
    private val nameAction = "callbackToApp"
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webViewHouseSDK)
        initWebView()
        onBack()
    }

    fun  onBack(){
        // Inside your Fragment or Activity class
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val url = webView.url
                if (url?.contains("house-model") == true) {
                    val webViewController = webView
                    val isCanGoBack = webViewController.canGoBack() ?: false

                    if (webViewController != null && isCanGoBack) {
                        // If the WebView can go back, navigate back in the WebView history
                        webViewController.goBack()
                        webViewController.reload()
                    } else {
                        // If the WebView cannot go back further, allow Android to handle back navigation
                        isEnabled = false
                        onBackPressed()
                    }
                } else {
                    // Allow Android to handle back navigation for other cases
                    isEnabled = false
                   onBackPressed()
                }
            }
        }
        // Register the onBackPressedCallback in your Fragment's onViewCreated or Activity's onCreate
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
    private fun initWebView() {
        webView.clearHistory()
        webView.clearFormData()
        webView.clearCache(true)
        CookieManager.getInstance().removeAllCookie()
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient() // Use WebViewClient to handle page navigation within WebView
        webView.loadUrl("https://house-model-stag.mypt.vn/#/")
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
            }
        }
        //file chooser
        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d(
                    "MyApplication webChromeClient", consoleMessage.message() + " -- From line "
                            + consoleMessage.lineNumber() + " of "
                            + consoleMessage.sourceId()
                )
                return true
            }
            override fun onShowFileChooser(
                webView: WebView?,
                callback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                //open image picker activi
                if (filePathCallback != null) {
                    filePathCallback!!.onReceiveValue(null)
                    filePathCallback = null
                }
                filePathCallback = callback // Store the callback to use it later
                // Create an intent to open the file picker
                val intent = fileChooserParams?.createIntent()
                startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE)
                return true
            }

        }
        webView.addJavascriptInterface(
            object : MyWebInterface {
                @JavascriptInterface
                override fun postMessage(message: String) {
                    if (message != "") {
                        val jsonObject = JSONObject(message)
                        val actionValue = jsonObject.getString("action")
                        println("message actionValue: $actionValue")
                        println("message : $message")
                        when (actionValue) {
                            getInputHouseSDK -> sendInputToWebView()
                            onBack -> clickOnBackWebView()
                            else -> println("Nothing")
                        }
                    }
                }
            },
            nameAction
        )

    }

    fun clickOnBackWebView() {
        webView.post {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun sendInputToWebView() {
        val key = "-----BEGIN RSA PUBLIC KEY-----\\nMIICCgKCAgEAlnmPSHu9sEo8WnHJWhZErjCk7HQNj45Lh27fClgnMwmnuJ8tYs2F\\nOCmsaE2ufhTaYZSENmLyETz1Rnxuhvac6tXjZ9qv9myT3sV1y5mvV5p3UGKVocLT\\nUiQAmscZoM+w1XN2yJb2E6J0nc+C2uNc5qwLz2J6aEmDEHLMdBGY01HelnaxHrkD\\nhiiMuLHAGLtjA6j9k0rJ88kBBbixnH6hE+8OUEX2fGtufP8bu4Z9Etfuq46t+7Sy\\nsg64Rr0N3RpfZbOl89wBlQhqUF4+YMTyqX5dqYslFUgehwh84Tm/r+CbAMEMzp0c\\nM4ge8oCNg4P9ShbPBw6CJztMZWl0UWf7vfYT4AdlNpDbzFPb65tpNUXpELL3Tmzg\\nafVpGgtDH6DbeYCqWFS/n0WVWCGBEI+gkdXq5mEIFG0guuts9gKUJ/kfVpB7HTWo\\npon2QVPiFbBmapwyl68rtGA6OaBZqDnIjuQyPDDU5+dws3iK6zs5Dq+SDYA5wgg8\\nv1kiJoe2YVXx0eJl3sZZlPRs2ZqsS9Z1sDICILRFUGpzN3o/QVl7n9VycAoFzTrI\\nYvsascv3VG+e8sngBPgPT5gSBQqh2/yQAl4T5zaLGfz72DUhEZC0B4LludbKTRg8\\nCms0bhy0sIufPw7NB8No/zEmTz4auVzkx+BUnJVrKBLu1PBnMndFdKMCAwEAAQ==\\n-----END RSA PUBLIC KEY-----\\n"
        val dataSdkInput = DataSdkInput(
            environment = "staging",
            appId = "mypt",
            insideAcc = "test",
            name = "Nguyễn Hữu Tường",
            publicKey = key.replace("replacenhere", "\n"),
            curDt = "2023-07-20",
            deviceId = "123456789",
            deviceName = "Phone 12 Pro Max",
            deviceToken = "device_token_here",
            devicePlatform = "Android",
            contractNo = "SYH142870",
            email = "john.doe@example.com"
        )
        val gson = Gson()
        // Convert the 'person' object to a JSON string
        val jsonString = gson.toJson(dataSdkInput)
        val javascriptCode = "callbackToApp.onMessage('$jsonString');"
        println("javascriptCode: $javascriptCode")
        webView.post {
            webView.evaluateJavascript(javascriptCode, null)

        }
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


interface MyWebInterface {
    @JavascriptInterface
    fun postMessage(message: String)
}

data class DataSdkInput(
    val environment: String,
    val appId: String,
    val insideAcc: String,
    val name: String,
    val publicKey: String,
    val curDt: String,
    val deviceId: String,
    val deviceName: String,
    val deviceToken: String,
    val devicePlatform: String,
    val contractNo: String,
    val email: String
)
