package com.viz.prodzen

import android.content.pm.ResolveInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppListScreen(installedApps: List<ResolveInfo>, onAppClick: (ResolveInfo) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(installedApps) { app ->
            AppItem(app = app, onClick = { onAppClick(app) })
        }
    }
}

@Composable
fun AppItem(app: ResolveInfo, onClick: () -> Unit) {
    val context = LocalContext.current
    val icon: ImageBitmap = app.loadIcon(context.packageManager).toBitmap().asImageBitmap()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(bitmap = icon, contentDescription = null, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = app.loadLabel(context.packageManager).toString())
    }
}