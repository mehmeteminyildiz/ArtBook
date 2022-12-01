package com.yildiz.artbook.repository

import androidx.lifecycle.LiveData
import com.yildiz.artbook.model.ImageResponse
import com.yildiz.artbook.roomdb.Art
import com.yildiz.artbook.util.Resource

/**
created by Mehmet E. Yıldız
 **/
interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt (art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>


}