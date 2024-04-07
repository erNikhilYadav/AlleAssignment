package com.nikhilyadav.alleassignment.ui.feature.permission

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.nikhilyadav.alleassignment.R
import com.nikhilyadav.alleassignment.ui.theme.AlleAssignmentTheme


@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun RequestPermissionScreen(
    permissionState: PermissionState,
    rejectCLick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_permission_unlock),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            text = "Access to your screenshots",
        )
        Text(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "We need your permission to access screenshots stored on your device.",
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            OutlinedButton(
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
                onClick = rejectCLick
            ) {
                Text(text = "Reject")
            }
            Button(
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    permissionState.launchPermissionRequest()
                }
            ) {
                Text(text = "Accept")
            }
        }

    }

}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
fun RequestPermissionScreenPreview() {
    AlleAssignmentTheme {
        RequestPermissionScreen(
            permissionState = rememberPermissionState(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {

        }
    }
}
