package com.yildiz.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.yildiz.artbook.databinding.ImageRowBinding
import javax.inject.Inject

/**
created by Mehmet E. Yıldız
 **/
class ImageRecyclerAdapter
@Inject constructor(val glide: RequestManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ImageRowViewHolder(val binding: ImageRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var images: List<String?>?
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageRowViewHolder(
            ImageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindImageRowViewHolder(holder as ImageRowViewHolder, position)
    }

    private fun bindImageRowViewHolder(
        holder: ImageRowViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            val url = images?.get(position)
            glide.load(url).into(imgArt)

            imgArt.setOnClickListener {
                onItemClickListener?.let { listener ->
                    url?.let {
                        listener(url)
                    }
                }
            }
        }

    }

    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = images?.size ?: 0


}