package com.yildiz.artbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.yildiz.artbook.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
created by Mehmet E. Yıldız
 **/

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase


    //    private lateinit var database: ArtDatabase  bunu yoruma alma sebebim: Hilt testi sırasında karışıklık çıkmaması için
    private lateinit var dao: ArtDAO

    @Before
    fun setup() {
        hiltRule.inject()
        // bunu yoruma alma sebebim: Hilt testi sırasında karışıklık çıkmaması için
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            ArtDatabase::class.java
//        ).allowMainThreadQueries().build()

        dao = database.artDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlocking {
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test.com", 1)
        dao.insertArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()

        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runBlocking {
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test.com", 1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }
}





















