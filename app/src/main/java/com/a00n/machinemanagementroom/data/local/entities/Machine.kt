package com.a00n.machinemanagementroom.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "machine")
data class Machine(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ref: String,
    val price: String,
//    val purchaseDate: Date,
    @ColumnInfo(name = "marqueId")
    val marqueId: Int
)
