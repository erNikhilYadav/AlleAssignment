package com.nikhilyadav.alleassignment.ui.feature.gallery.screens

import android.Manifest
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nikhilyadav.alleassignment.components.AsyncImageView
import com.nikhilyadav.alleassignment.ui.feature.gallery.GalleryViewModel
import com.nikhilyadav.alleassignment.ui.feature.permission.RequestPermissionScreen
import com.nikhilyadav.alleassignment.ui.theme.AlleAssignmentTheme
import com.nikhilyadav.alleassignment.utils.rememberCurrentPhoto
import com.nikhilyadav.alleassignment.utils.rememberMediaPhotos


private const val TAG = "GalleryScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel,
) {
    val selectedIndex by viewModel.selectedImage.collectAsState(initial = 0)
    val listState = rememberLazyListState()
    val viewportWidth = LocalConfiguration.current.screenWidthDp.dp
    val widthOfItem = viewportWidth / 9
    val heightOfItem = 60.dp
    val heightOfSelectedItem = 70.dp

    val photos = rememberMediaPhotos(context = LocalContext.current)

    Box(modifier = Modifier) {
        if (photos.isNotEmpty()) {
            if (selectedIndex >= 0) {
                AsyncImageView(
                    imgRequest = ImageRequest.Builder(LocalContext.current)
                        .data(photos[selectedIndex].uri)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = "Something Went Wrong",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Text(
                text = "Something Went Wrong",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }

        Column(
            verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize()
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(photos) { index, item ->
                    val transitionState =
                        remember { MutableTransitionState(selectedIndex != index) }
                    val targetState = selectedIndex == index
                    transitionState.targetState = targetState
                    val height by animateDpAsState(
                        targetValue = if (selectedIndex == index) heightOfSelectedItem else heightOfItem,
                        animationSpec = tween(durationMillis = 200), label = ""
                    )
                    DummyPlaceholders(index, widthOfItem, 0)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(height)
                            .width(widthOfItem)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Black, Color.Black)
                                ),
                                shape = RoundedCornerShape(10f),
                                alpha = 0.2f
                            )
                            .padding(2.dp)
                            .clip(RoundedCornerShape(10f))
                    ) {
                        AsyncImageView(
                            imgRequest = ImageRequest.Builder(LocalContext.current)
                                .data(item.uri)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .build(), modifier = Modifier.fillMaxSize()
                        )
                    }

                    DummyPlaceholders(index, widthOfItem, photos.size - 1)
                }
            }
        }
    }
    rememberCurrentPhoto(listState, viewModel)
}

@Composable
fun DummyPlaceholders(index: Int, widthOfItem: Dp, condition: Int) {
    if (index == condition) {
        for (i in 1..4) {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(widthOfItem)
                    .padding(2.dp)
            )
        }
    }
}

@Composable
@Preview
fun GalleryScreenPreview() {
    AlleAssignmentTheme {
        GalleryScreen(GalleryViewModel())
    }
}