package com.viz.prodzen

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    // Using MutableState instead of StateFlow
    private val _installedApps = mutableStateOf<List<ResolveInfo>>(emptyList())
    val installedApps: State<List<ResolveInfo>> = _installedApps

    var selectedApp by mutableStateOf<ResolveInfo?>(null)
        private set

    fun loadInstalledApps(packageManager: PackageManager) {
        _installedApps.value = getInstalledApps(packageManager)
    }

    fun selectApp(app: ResolveInfo) {
        selectedApp = app
    }

    fun clearSelection() {
        selectedApp = null
    }
}
