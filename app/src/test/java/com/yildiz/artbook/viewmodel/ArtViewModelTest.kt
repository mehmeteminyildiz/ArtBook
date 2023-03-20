package com.yildiz.artbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.yildiz.artbook.MainCoroutineRule
import com.yildiz.artbook.getOrAwaitValueTest
import com.yildiz.artbook.repo.FakeArtRepository
import com.yildiz.artbook.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
created by Mehmet E. Yıldız
 **/
@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        // test doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`() {
        viewModel.makeArt("Mona Lisa", "Da Vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        // üstteki satır ile birlikte, value'yu LiveData olmaktan kurtardık
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`() {
        viewModel.makeArt("", "Da Vinci", "1980")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        // üstteki satır ile birlikte, value'yu LiveData olmaktan kurtardık
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`() {
        viewModel.makeArt("Mona Lisa", "", "1980")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        // üstteki satır ile birlikte, value'yu LiveData olmaktan kurtardık
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}