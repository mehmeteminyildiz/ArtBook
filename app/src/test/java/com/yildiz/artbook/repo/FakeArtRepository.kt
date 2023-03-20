package com.yildiz.artbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yildiz.artbook.model.ImageResponse
import com.yildiz.artbook.repository.ArtRepositoryInterface
import com.yildiz.artbook.roomdb.Art
import com.yildiz.artbook.util.Resource

/**
created by Mehmet E. Yıldız
 **/
class FakeArtRepository : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)


    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(0, 0, arrayListOf()))
    }

    private fun refreshData() {
        artsLiveData.postValue(arts)
    }
}