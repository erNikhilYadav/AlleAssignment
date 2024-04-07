package com.nikhilyadav.alleassignment.ui.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {
    private val _selectedImage = MutableStateFlow(0)
    val selectedImage: StateFlow<Int> = _selectedImage

    fun selectedImage(newIndex: Int) {
        viewModelScope.launch {
            _selectedImage.emit(newIndex)
        }
    }

}