package com.viz.prodzen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

class ConfirmationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val packageName = intent.getStringExtra("com.viz.prodzen") ?: return

        setContent {
            ConfirmationDialog(
                appLabel = packageName,  // For simplicity, using the package name
                onContinue = {
                    val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                    startActivity(launchIntent)
                    finish()
                },
                onClose = {
                    finish()  // Close the confirmation screen (and app if needed)
                }
            )
        }
    }
}


@Composable
fun ConfirmationDialog(appLabel: String, onContinue: () -> Unit, onClose: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Do you want to continue using $appLabel?") },
            confirmButton = {
                Button(onClick = {
                    openDialog.value = false
                    onContinue()
                }) {
                    Text("Continue")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                    onClose()
                }) {
                    Text("Close App")
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewConfirmationDialog() {
    ConfirmationDialog(
        appLabel = "Example App",
        onContinue = { /* Continue action */ },
        onClose = { /* Close action */ }
    )
}