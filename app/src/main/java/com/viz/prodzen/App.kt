package com.viz.prodzen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyApp(context: Context) {
    val viewModel: AppViewModel = viewModel()
    val installedApps by viewModel.installedApps

    LaunchedEffect(Unit) {
        viewModel.loadInstalledApps(context.packageManager)
    }

    if (viewModel.selectedApp != null) {
        ConfirmScreen(
            appLabel = viewModel.selectedApp?.loadLabel(context.packageManager).toString(),
            onClose = { viewModel.clearSelection() },
            onContinue = {
                openApp(context, viewModel.selectedApp!!.activityInfo.packageName)
            }
        )
    } else {
        AppListScreen(
            installedApps = installedApps,
            onAppClick = { app -> viewModel.selectApp(app) }
        )
    }
}
