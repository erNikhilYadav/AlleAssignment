package com.nikhilyadav.alleassignment.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import com.nikhilyadav.alleassignment.ui.feature.gallery.GalleryViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

private const val TAG = "rememberCurrentPhoto"

@Composable
internal fun rememberCurrentPhoto(listState: LazyListState, viewModel: GalleryViewModel) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(coroutineScope) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect() { firstIndex ->
                firstIndex + 4
                viewModel.selectedImage(firstIndex + 4)
            }
    }
}