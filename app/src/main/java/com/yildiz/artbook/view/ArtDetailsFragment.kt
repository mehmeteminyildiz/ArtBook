package com.yildiz.artbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yildiz.artbook.databinding.FragmentArtDetailsBinding
import timber.log.Timber

class ArtDetailsFragment : Fragment() {
    private var _binding: FragmentArtDetailsBinding? = null
    private val binding: FragmentArtDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setCallBack()
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
        }
    }

    private fun initialize() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.e("onDestroy ArtDetailsFragment")
    }
}