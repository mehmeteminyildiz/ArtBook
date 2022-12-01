package com.yildiz.artbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yildiz.artbook.databinding.FragmentArtsBinding
import timber.log.Timber

class ArtFragment : Fragment() {

    private var _binding: FragmentArtsBinding? = null
    private val binding: FragmentArtsBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
    }

    private fun handleClickEvents() {
        binding.apply {
            fab.setOnClickListener {
                val action = ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun initialize() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.e("onDestroy ArtFragment")
    }
}