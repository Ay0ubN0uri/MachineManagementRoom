package com.a00n.machinemanagementroom.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a00n.machinemanagementroom.data.local.entities.Marque
import com.a00n.machinemanagementroom.databinding.MarqueItemBinding

class MarqueAdapter(val onClickListener: (View, Marque) -> Unit) :
    ListAdapter<Marque, MarqueAdapter.MarqueViewHolder>(MarqueItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarqueViewHolder {
        val binding = MarqueItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MarqueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarqueViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MarqueViewHolder(private val binding: MarqueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(marque: Marque) {
            binding.marqueMaterialCardView.setOnClickListener { view ->
                this@MarqueAdapter.onClickListener(view, marque)
            }
            binding.marque = marque
        }
    }
}