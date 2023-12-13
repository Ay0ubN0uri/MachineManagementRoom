package com.a00n.machinemanagementroom.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marque")
data class Marque(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val code: String,
    val name: String
)
