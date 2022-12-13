package com.yildiz.artbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.yildiz.artbook.databinding.FragmentArtDetailsBinding
import com.yildiz.artbook.util.Status
import com.yildiz.artbook.viewmodel.ArtViewModel
import timber.log.Timber
import javax.inject.Inject

class ArtDetailsFragment
@Inject constructor(
    val glide: RequestManager
) : Fragment() {
    private var _binding: FragmentArtDetailsBinding? = null
    private val binding: FragmentArtDetailsBinding get() = _binding!!

    lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToObservers()
//        setCallBack()
        handleClickEvents()
    }

    /** geri bastığımızda örneğin, veri aktarmak istiyorsak böyle bir yapı kullanabilirmişiz sanırım :D **/
    private fun setCallBack() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Timber.e("onBack")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun handleClickEvents() {
        binding.apply {
            imgArt.setOnClickListener {
                val action =
                    ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
                findNavController().navigate(action)
            }
            btnSave.setOnClickListener {
                viewModel.makeArt(
                    etName.text.toString(),
                    etArtist.text.toString(),
                    etYear.text.toString()
                )

            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.selectedImageURL.observe(viewLifecycleOwner, Observer { url ->
            Timber.e("xxx1 url: $url")
            glide.load(url).into(binding.imgArt)

            binding?.let {
                Timber.e("xxx2 url: $url")
                glide.load(url).into(it.imgArt)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.message ?: "error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.LOADING -> {


                }
            }
        })

    }

    private fun initialize() {
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.e("onDestroy ArtDetailsFragment")
    }
}