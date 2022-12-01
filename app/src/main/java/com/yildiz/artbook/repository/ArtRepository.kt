package com.yildiz.artbook.repository

import androidx.lifecycle.LiveData
import com.yildiz.artbook.api.RetrofitAPI
import com.yildiz.artbook.model.ImageResponse
import com.yildiz.artbook.roomdb.Art
import com.yildiz.artbook.roomdb.ArtDAO
import com.yildiz.artbook.util.Resource
import javax.inject.Inject

/**
created by Mehmet E. Yıldız
 **/
class ArtRepository
@Inject constructor
    (
    private val artDAO: ArtDAO,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDAO.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDAO.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDAO.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }

}