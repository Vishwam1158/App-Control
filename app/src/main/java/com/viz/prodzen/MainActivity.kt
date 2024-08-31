package com.viz.prodzen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.viz.prodzen.ui.theme.ProdZenTheme
import androidx.lifecycle.viewmodel.compose.viewModel  // For using viewModel in Compose
import androidx.compose.runtime.getValue                // For `by` keyword with State
import androidx.compose.runtime.setValue                // For State modification
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.collectAsState



//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val sharedPreferences = getSharedPreferences("ProdZenPrefs", Context.MODE_PRIVATE)
//        val viewModelFactory = AppViewModelFactory(sharedPreferences)
//
//
//        enableEdgeToEdge()
//        setContent {
//            val viewModel: AppViewModel = viewModel(factory = viewModelFactory)
//            MyApp(viewModel)
//        }
//    }
//}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: AppViewModel = viewModel(factory = AppViewModelFactory(packageManager))
            MyApp(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(viewModel: AppViewModel) {
    val installedApps by viewModel.installedApps.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Select Apps") })
        },
        content = {
                    Spacer(modifier = Modifier.padding(it))
            AppListScreen(installedApps = installedApps, viewModel = viewModel)
        }
    )
}



fun openApp(context: Context, packageName: String) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    }
}

