package com.a00n.machinemanagementroom.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MachineWithMarque(
    @Embedded val machine: Machine,
    @Relation(
        parentColumn = "marqueId",
        entityColumn = "id"
    )
    val marque: Marque
)

