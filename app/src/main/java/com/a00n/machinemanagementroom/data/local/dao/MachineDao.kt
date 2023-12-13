package com.a00n.machinemanagementroom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.a00n.machinemanagementroom.data.local.entities.Machine
import com.a00n.machinemanagementroom.data.local.entities.MachineWithMarque
import com.a00n.machinemanagementroom.data.local.entities.MarqueWithMachineCount

@Dao
interface MachineDao {
    @Insert
    suspend fun insert(machine: Machine)

    @Update
    suspend fun update(machine: Machine)

    @Delete
    suspend fun delete(machine: Machine)

    @Query("DELETE FROM machine")
    suspend fun deleteAll()

    @Query("SELECT * FROM machine WHERE id = :id")
    fun get(id: Long): LiveData<Machine>

    @Query("SELECT * FROM machine ORDER BY id DESC")
    fun getAll(): LiveData<List<Machine>>

    @Query("SELECT * FROM machine WHERE marqueId = :marqueId")
    fun getMachinesByMarque(marqueId: Int): LiveData<List<Machine>>

    @Transaction
    @Query("SELECT * FROM machine")
    fun getAllMachinesWidthMarque(): LiveData<List<MachineWithMarque>>

    @Transaction
    @Query("SELECT *, COUNT(machine.id) AS machineCount FROM marque LEFT JOIN machine ON marque.id = machine.marqueId GROUP BY marque.id")
    fun getMarquesWithMachineCount(): LiveData<List<MarqueWithMachineCount>>
}