package com.yildiz.artbook.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yildiz.artbook.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        // fragmentFactory'nin kullanılacağını belirtiyoruz
        setContentView(R.layout.activity_main)
    }
}