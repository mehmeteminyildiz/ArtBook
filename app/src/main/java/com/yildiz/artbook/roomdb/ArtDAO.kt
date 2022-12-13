package com.yildiz.artbook.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art: Art)

    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>
}