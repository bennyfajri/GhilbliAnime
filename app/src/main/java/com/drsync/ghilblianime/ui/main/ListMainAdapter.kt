package com.drsync.ghilblianime.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drsync.ghilblianime.databinding.ItemListBinding
import com.drsync.ghilblianime.models.ServerResponse
import com.drsync.ghilblianime.util.Constants.createProgress

class ListMainAdapter : ListAdapter<ServerResponse, ListMainAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ServerResponse, position: Int) {
            with(binding) {
                //set text
                val durasi =  "Durasi : ${data.running_time} menit"
                val score =  "Score : ${data.rt_score}/100"

                //set view
                tvTitle.text = data.title
                tvDuration.text = durasi
                tvScore.text = score
                Glide.with(itemView.context)
                    .load(data.image)
                    .placeholder(itemView.context.createProgress())
                    .error(android.R.color.darker_gray)
                    .into(imgFilm)
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(data, position)
                }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ServerResponse, position: Int)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ServerResponse> =
            object : DiffUtil.ItemCallback<ServerResponse>() {
                override fun areItemsTheSame(oldItem: ServerResponse, newItem: ServerResponse): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ServerResponse, newItem: ServerResponse): Boolean {
                    return oldItem == newItem
                }
            }
    }
}