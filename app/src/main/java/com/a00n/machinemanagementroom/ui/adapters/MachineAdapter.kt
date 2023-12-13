package com.a00n.machinemanagementroom.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a00n.machinemanagementroom.data.local.entities.Machine
import com.a00n.machinemanagementroom.data.local.entities.MachineWithMarque
import com.a00n.machinemanagementroom.databinding.MachineItemBinding

class MachineAdapter(val onClickListener: (View, MachineWithMarque) -> Unit) :
    ListAdapter<MachineWithMarque, MachineAdapter.MachineViewHolder>(MachineItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachineViewHolder {
        val binding = MachineItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MachineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MachineViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MachineViewHolder(private val binding: MachineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(machine: MachineWithMarque) {
            binding.machineMaterialCardView.setOnClickListener { view ->
                this@MachineAdapter.onClickListener(view, machine)
            }
            binding.machine = machine
        }
    }
}