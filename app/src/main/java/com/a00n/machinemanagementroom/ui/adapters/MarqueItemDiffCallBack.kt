package com.a00n.machinemanagementroom.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.a00n.machinemanagementroom.data.local.entities.Marque

class MarqueItemDiffCallBack : DiffUtil.ItemCallback<Marque>() {
    override fun areItemsTheSame(oldItem: Marque, newItem: Marque): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Marque, newItem: Marque): Boolean {
        return oldItem == newItem
    }
}