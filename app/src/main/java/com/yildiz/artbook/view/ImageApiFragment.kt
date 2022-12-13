package com.yildiz.artbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.yildiz.artbook.adapter.ImageRecyclerAdapter
import com.yildiz.artbook.databinding.FragmentImageApiBinding
import com.yildiz.artbook.util.Status
import com.yildiz.artbook.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ImageApiFragment
@Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment() {
    private var _binding: FragmentImageApiBinding? = null
    private val binding: FragmentImageApiBinding get() = _binding!!

    lateinit var viewModel: ArtViewModel


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
        setAdapter()
        subscribeToObservers()
        textChangeListener()
    }

    private fun textChangeListener() {
        binding.apply {
            var job: Job? = null
            etSearch.addTextChangedListener {
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(1000)
                    it?.let {
                        if (it.toString().isNotEmpty()) {
                            viewModel.searchForImage(it.toString())
                        }
                    }
                }
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.message ?: "hata var "}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setAdapter() {
        binding.apply {
            rvImages.adapter = imageRecyclerAdapter
            rvImages.layoutManager = GridLayoutManager(requireContext(), 3)
            imageRecyclerAdapter.setOnItemClickListener {
                findNavController().popBackStack()
                viewModel.setSelectedImage(it)
            }
        }
    }

    private fun initialize() {
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.e("onDestroy FragmentImageApi")
    }
}