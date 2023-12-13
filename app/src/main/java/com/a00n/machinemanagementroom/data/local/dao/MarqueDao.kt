package com.a00n.machinemanagementroom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.a00n.machinemanagementroom.data.local.entities.Marque
import com.a00n.machinemanagementroom.data.local.entities.MarqueWithMachines

@Dao
interface MarqueDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marque: Marque): Long

    @Update
    suspend fun update(marque: Marque)

    @Delete
    suspend fun delete(marque: Marque)

    @Query("DELETE FROM marque")
    suspend fun deleteAll()

    @Query("SELECT * FROM marque WHERE id = :id")
    fun get(id: Long): LiveData<Marque>

    @Query("SELECT * FROM marque ORDER BY id DESC")
    fun getAll(): LiveData<List<Marque>>

    @Transaction
    @Query("SELECT * FROM marque WHERE id = :marqueId")
    fun getMarqueWithMachines(marqueId: Int): LiveData<MarqueWithMachines>

    @Transaction
    @Query("SELECT * FROM marque")
    fun getAllMarqueWithMachines(): LiveData<List<MarqueWithMachines>>
}