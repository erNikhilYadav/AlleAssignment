package com.nikhilyadav.alleassignment.ui.feature.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nikhilyadav.alleassignment.components.AsyncImageView
import com.nikhilyadav.alleassignment.ui.feature.GalleryViewModel
import com.nikhilyadav.alleassignment.ui.theme.AlleAssignmentTheme
import com.nikhilyadav.alleassignment.utils.DatasetUtil

private const val TAG = "GalleryScreen"

@Composable
fun GalleryScreen(viewModel: GalleryViewModel) {
    val listState = rememberLazyListState()
    val selectedIndex by viewModel.selectedImage.collectAsState(initial = 0)
    val viewportWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = Modifier) {
        AsyncImageView(
            imgRequest = ImageRequest.Builder(LocalContext.current)
                .data(DatasetUtil.dataset[selectedIndex])
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize()
        )
        Column(
            verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize()
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                DatasetUtil.dataset.forEachIndexed { index, item ->
                    item {
//                        val scale = if (index == selectedIndex) 1.2f else 1f
//                        Log.d(TAG, "ImageRow: $index <<>> DatasetUtil ${DatasetUtil.dataset.size} ")
                        if (index == 0) {
//                            Spacer(modifier = Modifier.width((viewportWidth / 2) - (viewportWidth / 10) / 2)
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(if (index == selectedIndex) 70.dp else 60.dp)
                                .width(if (index == selectedIndex) viewportWidth / 10 else viewportWidth / 9)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Black, Color.Black)
                                    ),
                                    shape = RoundedCornerShape(10f),
                                    alpha = 0.2f
                                )
                                .padding(2.dp)
                                .clip(RoundedCornerShape(10f))
                                .clickable {
                                    viewModel.selectedImage(index)
                                }) {

                            AsyncImageView(
                                imgRequest = ImageRequest.Builder(LocalContext.current)
                                    .data(item)
                                    .diskCachePolicy(CachePolicy.ENABLED)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .build(), modifier = Modifier.fillMaxSize()
                            )
                        }

                        if (index == (DatasetUtil.dataset.size - 1)) {
//                            Spacer(modifier = Modifier.width((viewportWidth / 2) - (viewportWidth / 10) / 2))
                        }
                    }
                }
            }

            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
                    val avg = listState.layoutInfo.visibleItemsInfo.size
                    Log.d(TAG, "ImageRow: $index <<>> avg $avg ")
                    if (index <= avg)
                        viewModel.selectedImage(index)
                    else
                        viewModel.selectedImage(avg + index)
                }

//                snapshotFlow { listState }.collect{state ->
//                    Log.d(TAG, "ImageRow: ${state.isScrollInProgress} <<>> avg ${state.firstVisibleItemIndex} ")
//                    if (!state.isScrollInProgress) {
//                        viewModel.selectedImage(listState.layoutInfo.visibleItemsInfo.size / 2 + state.firstVisibleItemIndex)
//                    }
//                }

//                snapshotFlow { listState.isScrollInProgress }
//                    .distinctUntilChanged()
//                    .filter { it } // Trigger only when scrolling starts
//                    .collect {
//                    }
            }
        }
    }
}


fun setDynamicPadding(index: Int, viewportWidth: Dp): Dp {
    return if (index == 0 || index == DatasetUtil.dataset.size) {
        return viewportWidth / 2
    } else 2.dp
}

@Composable
@Preview
fun GalleryScreenPreview() {
    AlleAssignmentTheme {
        GalleryScreen(GalleryViewModel())
    }
}