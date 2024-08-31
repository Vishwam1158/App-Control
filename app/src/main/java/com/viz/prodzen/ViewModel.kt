package com.viz.prodzen

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



class AppViewModel(private val packageManager: PackageManager) : ViewModel() {

    // State to hold the list of installed apps
    private val _installedApps = MutableStateFlow<List<ResolveInfo>>(emptyList())
    val installedApps: StateFlow<List<ResolveInfo>> = _installedApps

    private val _selectedApps = mutableSetOf<String>()

    init {
        // Load the installed apps when the ViewModel is initialized
        loadInstalledApps()
    }

    // Function to load installed apps
    fun loadInstalledApps() {
        _installedApps.value = getInstalledApps(packageManager)
    }

    // Function to retrieve the list of installed apps
    private fun getInstalledApps(packageManager: PackageManager): List<ResolveInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        return packageManager.queryIntentActivities(intent, 0)
    }

    fun toggleAppSelection(app: ResolveInfo) {
        val packageName = app.activityInfo.packageName
        if (_selectedApps.contains(packageName)) {
            _selectedApps.remove(packageName)
        } else {
            _selectedApps.add(packageName)
        }
    }

    fun isAppSelected(packageName: String): Boolean {
        return _selectedApps.contains(packageName)
    }
}



// Factory to create the AppViewModel
class AppViewModelFactory(private val packageManager: PackageManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(packageManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}