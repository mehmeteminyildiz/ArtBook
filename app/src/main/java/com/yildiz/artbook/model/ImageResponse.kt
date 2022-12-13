package com.yildiz.artbook.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("totalHits") var totalHits: Int? = null,
    @SerializedName("hits") var hits: ArrayList<Hits> = arrayListOf()
)
