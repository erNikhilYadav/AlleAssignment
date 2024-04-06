package com.nikhilyadav.alleassignment.ui.feature

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


//    fun getImages(context: Context, zipFileName: String): List<Bitmap> {
//        return readZipFile(context, zipFileName)
//    }
//
//    private fun readZipFile(context: Context, zipFileName: String): List<Bitmap> {
//        val assetManager = context.assets
//        val inputStream = assetManager.open(zipFileName)
//        val zipInputStream = ZipInputStream(inputStream)
//
//        val images = mutableListOf<Bitmap>()
//        var entry: ZipEntry? = zipInputStream.nextEntry
//
//        while (entry != null) {
//            if (!entry.isDirectory && entry.name.endsWith(".png")) {
//                val imageBytes = zipInputStream.readBytes()
//                val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(imageBytes))
//                images.add(bitmap)
//            }
//            entry = zipInputStream.nextEntry
//        }
//
//        zipInputStream.close()
//        return images
//    }
}