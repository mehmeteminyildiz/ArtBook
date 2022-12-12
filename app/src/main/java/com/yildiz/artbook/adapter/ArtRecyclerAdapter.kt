package com.yildiz.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.yildiz.artbook.databinding.ArtRowBinding
import com.yildiz.artbook.roomdb.Art
import javax.inject.Inject

/**
created by Mehmet E. Yıldız
 **/
class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ArtViewHolder(val binding: ArtRowBinding) : RecyclerView.ViewHolder(binding.root)

    // verilerdeki item'larda değişiklik varsa, değişikliği yansıtmak için kullanılıyor.
    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArtViewHolder(
            ArtRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindArtViewHolder(holder as ArtViewHolder, position)
    }

    private fun bindArtViewHolder(holder: ArtViewHolder, position: Int) {
        val item = arts[position]
        holder.binding.apply {
            glide.load(item.imageUrl).into(imgArt)
            tvArtName.text = item.name
            tvArtistName.text = item.artistName
            tvArtYear.text = item.year.toString()
        }
    }

    override fun getItemCount(): Int = arts.size

}