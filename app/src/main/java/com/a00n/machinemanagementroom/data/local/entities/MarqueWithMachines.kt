package com.a00n.machinemanagementroom.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MarqueWithMachines(
    @Embedded
    val marque: Marque,
    @Relation(
        parentColumn = "id",
        entityColumn = "marqueId"
    )
    val machines: List<Machine>
)
