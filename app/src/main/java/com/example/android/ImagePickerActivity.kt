package com.example.android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback

class ImagePickerActivity : Activity() {

    private var filePathCallback: ValueCallback<Array<android.net.Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_IMAGE_PICKER) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImageUri = data.data
                if (selectedImageUri != null) {
                    filePathCallback?.onReceiveValue(arrayOf(selectedImageUri))
                } else {
                    // The selectedImageUri is null, which means the user canceled the selection
                    filePathCallback?.onReceiveValue(null)
                }
            } else {
                // User canceled the selection
                filePathCallback?.onReceiveValue(null)
            }
        } else {
            filePathCallback?.onReceiveValue(null)
        }
        finish()
    }

    companion object {
        const val REQUEST_CODE_IMAGE_PICKER = 101
    }

    // Public method to handle the result from the hosting activity
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        onActivityResult(requestCode, resultCode, data)
    }
}

