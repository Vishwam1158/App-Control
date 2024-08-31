package com.viz.prodzen
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.content.SharedPreferences
import android.view.accessibility.AccessibilityEvent

class AppMonitorService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            val packageName = it.packageName?.toString()
            if (packageName != null && isAppSelected(packageName)) {
                showConfirmationScreen(packageName)
            }
        }
    }

    override fun onInterrupt() {}

    private fun isAppSelected(packageName: String): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("ProdZenPrefs", MODE_PRIVATE)
        val selectedApps = sharedPreferences.getStringSet("selected_apps", emptySet())
        return selectedApps?.contains(packageName) == true
    }

    private fun showConfirmationScreen(packageName: String) {
        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("PACKAGE_NAME", packageName)
        }
        startActivity(intent)
    }
}
