package com.viz.prodzen

//
import android.content.pm.ResolveInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyApp(viewModel: AppViewModel) {
//    val context = LocalContext.current
//    val installedApps by viewModel.installedApps.collectAsState(emptyList())
//
//    LaunchedEffect(Unit) {
//        viewModel.loadInstalledApps(context.packageManager)
//    }
//
//    Scaffold(
//        topBar = {
//
//            TopAppBar(title = { Text("Select Apps") })
//        }
//    ) {
//        Spacer(modifier = Modifier.height(it.calculateTopPadding()))
//        AppListScreen(installedApps = installedApps, viewModel = viewModel)
//    }
//}
//
@Composable
fun AppListScreen(installedApps: List<ResolveInfo>, viewModel: AppViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(installedApps) { app ->
            AppItem(app = app, viewModel = viewModel)
        }
    }
}

@Composable
fun AppItem(app: ResolveInfo, viewModel: AppViewModel) {
    val context = LocalContext.current
    val packageName = app.activityInfo.packageName
    val isSelected = viewModel.isAppSelected(packageName)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { viewModel.toggleAppSelection(app) }
            .background(if (isSelected) Color.LightGray else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icon = app.loadIcon(context.packageManager).toBitmap().asImageBitmap()
        Image(bitmap = icon, contentDescription = null, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = app.loadLabel(context.packageManager).toString())
    }
}
