package com.yildiz.artbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yildiz.artbook.adapter.ImageRecyclerAdapter
import com.yildiz.artbook.databinding.FragmentImageApiBinding
import timber.log.Timber
import javax.inject.Inject

class ImageApiFragment
    @Inject constructor(
        private val imageRecyclerAdapter: ImageRecyclerAdapter
    )
    : Fragment() {
    private var _binding: FragmentImageApiBinding? = null
    private val binding: FragmentImageApiBinding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.e("onDestroy FragmentImageApi")
    }
}