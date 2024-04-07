package com.nikhilyadav.alleassignment.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import com.nikhilyadav.alleassignment.ui.feature.gallery.GalleryViewModel

@Composable
internal fun rememberCurrentPhoto(listState: LazyListState, viewModel: GalleryViewModel) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(coroutineScope) {
        snapshotFlow { listState.layoutInfo }.collect { visibleItems ->
            val visibleItemsSize = visibleItems.visibleItemsInfo.size
            val firstIndex = listState.firstVisibleItemIndex
            var indexToReturn: Int = -1
            if (firstIndex == 0) {
                indexToReturn = visibleItemsSize - 5
            } else {
                indexToReturn = firstIndex + 4
            }
            viewModel.selectedImage(indexToReturn)
        }
    }
}