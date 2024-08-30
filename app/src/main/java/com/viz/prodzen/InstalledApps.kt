package com.viz.prodzen

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.Intent

fun getInstalledApps(packageManager: PackageManager): List<ResolveInfo> {
    val intent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
    return packageManager.queryIntentActivities(intent, 0)
}
