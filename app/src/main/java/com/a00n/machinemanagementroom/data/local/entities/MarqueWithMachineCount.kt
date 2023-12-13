package com.a00n.machinemanagementroom.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class MarqueWithMachineCount(
    @Embedded val marque: Marque,
    @ColumnInfo(name = "machineCount") val machineCount: Int
)

