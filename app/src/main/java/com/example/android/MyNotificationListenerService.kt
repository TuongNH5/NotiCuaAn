import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class MyNotificationListenerService : NotificationListenerService() {

    companion object {
        private const val TAG = "MyNotificationListener"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val tickerText = sbn.notification.tickerText?.toString() ?: ""
        Log.d(TAG, "New notification posted: $packageName - $tickerText")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        Log.d(TAG, "Notification removed: $packageName")
    }
}
