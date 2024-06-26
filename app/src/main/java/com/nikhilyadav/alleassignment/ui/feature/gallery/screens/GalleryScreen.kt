package com.nikhilyadav.alleassignment.ui.feature.gallery.screens

import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nikhilyadav.alleassignment.components.AsyncImageView
import com.nikhilyadav.alleassignment.ui.feature.gallery.GalleryViewModel
import com.nikhilyadav.alleassignment.ui.theme.AlleAssignmentTheme
import com.nikhilyadav.alleassignment.utils.MediaPhoto
import com.nikhilyadav.alleassignment.utils.rememberCurrentPhoto
import com.nikhilyadav.alleassignment.utils.rememberMediaPhotos


private const val TAG = "GalleryScreen"

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

    var photos = mutableListOf<MediaPhoto>()
    addPlaceHoldersInList(photos)
    photos = (photos + rememberMediaPhotos(context = LocalContext.current)).toMutableList()
    addPlaceHoldersInList(photos)

    Box(modifier = Modifier.fillMaxSize()) {
        if (photos.isNotEmpty()) {
            if (photos.size > 8) {
                AsyncImageView(
                    imgRequest = ImageRequest.Builder(LocalContext.current)
                        .data(photos[selectedIndex].uri)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                ErrorView(errorMsg = "Screenshots not available")
            }
        } else {
            ErrorView(errorMsg = "Screenshots not available")
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
                    val height by animateDpAsState(
                        targetValue = if (selectedIndex == index) heightOfSelectedItem else heightOfItem,
                        animationSpec = tween(durationMillis = 400), label = ""
                    )
                    val width by animateDpAsState(
                        targetValue = if (selectedIndex == index) (widthOfItem + 2.dp) else widthOfItem,
                        animationSpec = tween(durationMillis = 400), label = ""
                    )
                    if (index in 0..3 || index in photos.size - 4..<photos.size) {
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .width(widthOfItem)
                        )
                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(height)
                                .width(width)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black,
                                            Color.Black
                                        )
                                    ),
                                    shape = RoundedCornerShape(10f),
                                    alpha = 0.2f
                                )
                                .padding(2.dp)
                                .zIndex(if (selectedIndex == index) 1.2f else 1f)
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
                    }
                }
            }
        }
    }

    rememberCurrentPhoto(listState, viewModel)
}

fun addPlaceHoldersInList(photos: MutableList<MediaPhoto>) {
    for (i in 1..4) {
        photos.add(MediaPhoto("", Uri.EMPTY, Size.Unspecified))
    }
}

@Composable
fun ErrorView(errorMsg: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMsg,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun GalleryScreenPreview() {
    AlleAssignmentTheme {
        GalleryScreen(GalleryViewModel())
    }
}