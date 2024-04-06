package com.nikhilyadav.alleassignment.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest

@Composable
fun AsyncImageView(imgRequest: ImageRequest, modifier: Modifier) {
    AsyncImage(
        model = imgRequest,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        filterQuality = FilterQuality.None,
        onState = {
            when (it) {
                AsyncImagePainter.State.Empty -> {

                }

                is AsyncImagePainter.State.Error -> {

                }

                is AsyncImagePainter.State.Loading -> {

                }

                is AsyncImagePainter.State.Success -> {
                }
            }
        }
    )
}