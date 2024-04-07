package com.nikhilyadav.alleassignment.utils

import android.util.Log
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
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.size }
            .distinctUntilChanged()
            .collect() { visibleItemsSize ->
                val firstIndex = listState.firstVisibleItemIndex
                val indexToReturn = if (firstIndex == 0) {
                    visibleItemsSize - 5
                } else {
                    firstIndex + 4
                }
                Log.d(TAG, "rememberCurrentPhoto: $indexToReturn ")
                viewModel.selectedImage(indexToReturn)
            }
    }
}