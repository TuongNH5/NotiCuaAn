package com.example.android

import MyNotificationListenerService
import android.app.Application
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
class MainActivity : AppCompatActivity() {
    private lateinit var stringViewModel: StringViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        listenerService()
        stringViewModel = ViewModelProvider(this).get(StringViewModel::class.java)
        val sendStringButton = findViewById<Button>(R.id.sendStringButton)
        val stringEditText = findViewById<EditText>(R.id.stringEditText)
        val responseTextView = findViewById<TextView>(R.id.responseTextView)
        sendStringButton.setOnClickListener {
            val string = stringEditText.text.toString()

            stringViewModel.postString(string)
        }

        stringViewModel.response.observe(this, Observer { response ->
            response?.let {
                responseTextView.text = it
            }
        })
    }

   private fun listenerService() {

       // Check if the app has permission to read notifications
       if (!isNotificationServiceEnabled()) {
           // If not, ask the user to grant permission
           val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
           startActivity(intent)
           Toast.makeText(this, "Please grant permission to read notifications", Toast.LENGTH_LONG).show()
       }

//       // Start the NotificationListenerService
//       val intent = Intent(this, MyNotificationListenerService::class.java)
//       startService(intent)


    }
    private fun isNotificationServiceEnabled(): Boolean {
        val cn = ComponentName(this, MyNotificationListenerService::class.java)
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(cn.flattenToString())
    }

}

class StringViewModel(application: Application) : AndroidViewModel(application) {

    val response = MutableLiveData<String>()

    fun postString(string: String) {
        val queue = Volley.newRequestQueue(getApplication())
        val jsonObject = JSONObject().apply {
            put("body", string)
        }
        val request = JsonObjectRequest(
            Request.Method.POST,
            "http://192.168.0.105:8000/blog",
            jsonObject, { response ->
                println(response.toString())
                this.response.value = response.toString()
            },
            { error ->
                this.response.value = error.toString()
            }
        )

        queue.add(request)
    }
}

