package com.a00n.machinemanagementroom.data.repository

import androidx.lifecycle.LiveData
import com.a00n.machinemanagementroom.data.local.dao.MachineDao
import com.a00n.machinemanagementroom.data.local.entities.Machine
import com.a00n.machinemanagementroom.data.local.entities.MachineWithMarque
import com.a00n.machinemanagementroom.data.local.entities.MarqueWithMachineCount

class MachineRepository(private val machineDao: MachineDao) {

    val machinesWithMarque: LiveData<List<MachineWithMarque>> =
        machineDao.getAllMachinesWidthMarque()
    val machines: LiveData<List<Machine>> = machineDao.getAll()
    val marquesWithMachineCount: LiveData<List<MarqueWithMachineCount>> =
        machineDao.getMarquesWithMachineCount()

    suspend fun insert(machine: Machine) {
        machineDao.insert(machine)
    }

    suspend fun delete(machine: Machine) {
        machineDao.delete(machine)
    }

    suspend fun deleteAll() = machineDao.deleteAll()
    suspend fun update(machine: Machine) {
        machineDao.update(machine)
    }


    suspend fun getMarquesWithMachineCount(): LiveData<List<MarqueWithMachineCount>> {
        return machineDao.getMarquesWithMachineCount()
    }

}