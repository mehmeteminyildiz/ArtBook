package com.yildiz.artbook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yildiz.artbook.adapter.ArtRecyclerAdapter
import com.yildiz.artbook.databinding.FragmentArtsBinding
import com.yildiz.artbook.viewmodel.ArtViewModel
import timber.log.Timber
import javax.inject.Inject

class ArtFragment
@Inject constructor(val artRecyclerAdapter: ArtRecyclerAdapter) : Fragment() {

    private var _binding: FragmentArtsBinding? = null
    private val binding: FragmentArtsBinding get() = _binding!!

    lateinit var viewModel: ArtViewModel

    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = artRecyclerAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedArt)
            }

        }

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
        setAdapters()
        subscribeToObserves()
        handleClickEvents()
    }

    private fun setAdapters() {
        binding.apply {
            rvArts.adapter = artRecyclerAdapter
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvArts.layoutManager = layoutManager

            ItemTouchHelper(swipeCallBack).attachToRecyclerView(rvArts)
        }
    }

    private fun handleClickEvents() {
        binding.apply {
            fab.setOnClickListener {
                val action = ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun subscribeToObserves() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })
    }

    private fun initialize() {
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.e("onDestroy ArtFragment")
    }
}