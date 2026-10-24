package com.shared.core.bluetooth_permission

import android.Manifest
import android.os.Build
import androidx.compose.runtime.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun RememberBluetoothPermission(
    onGranted: () -> Unit
) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Manifest.permission.BLUETOOTH_CONNECT
    } else {
        Manifest.permission.BLUETOOTH
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onGranted()
    }

    LaunchedEffect(Unit) {
        launcher.launch(permission)
    }
}