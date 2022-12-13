package com.yildiz.artbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yildiz.artbook.model.ImageResponse
import com.yildiz.artbook.repository.ArtRepositoryInterface
import com.yildiz.artbook.roomdb.Art
import com.yildiz.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
created by Mehmet E. Yıldız
 **/
// livedata'lara repository'deki fonksiyonların sonuçlarını işleyeceğiz.
// Böylece view kısmında bununla uğraşmayacağız
@HiltViewModel
class ArtViewModel
@Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    // Art Fragment
    val artList = repository.getArt()

    // Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>> get() = images

    val selectedImage = MutableLiveData<String>()
    val selectedImageURL: LiveData<String> get() = selectedImage

    // art details fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>> get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    // Mutable LiveData'larımızı tanımladık. Şimdi fonksiyonlarımızı yazmaya başlayalım

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Eksiksik doldurmalısınız", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Year must be integer", null))
            return
        }
        val art = Art(name, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))

    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) return

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }


}