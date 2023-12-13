package com.a00n.machinemanagementroom.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.a00n.machinemanagementroom.data.local.entities.Machine
import com.a00n.machinemanagementroom.data.local.entities.MachineWithMarque

class MachineItemDiffCallBack : DiffUtil.ItemCallback<MachineWithMarque>() {
    override fun areItemsTheSame(oldItem: MachineWithMarque, newItem: MachineWithMarque): Boolean {
        return oldItem.machine.id == newItem.machine.id
    }

    override fun areContentsTheSame(
        oldItem: MachineWithMarque,
        newItem: MachineWithMarque
    ): Boolean {
        return oldItem == newItem
    }
}