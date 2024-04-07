package com.nikhilyadav.alleassignment.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nikhilyadav.alleassignment.ui.feature.gallery.GalleryViewModel
import com.nikhilyadav.alleassignment.ui.feature.gallery.screens.GalleryScreen
import com.nikhilyadav.alleassignment.ui.feature.permission.RequestPermissionScreen
import com.nikhilyadav.alleassignment.ui.theme.AlleAssignmentTheme


class MainActivity : ComponentActivity() {
    private val viewModel: GalleryViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets -> insets }
        setContent {
            AlleAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val storagePermissionState = rememberPermissionState(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    if (!storagePermissionState.status.isGranted) {
                        RequestPermissionScreen(permissionState = storagePermissionState) {
                            Toast.makeText(
                                this,
                                "Permission is required to use this app.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        GalleryScreen(viewModel)
                    }
                }
            }
        }
    }
}





