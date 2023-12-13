package com.a00n.machinemanagementroom.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.a00n.machinemanagementroom.data.local.dao.MarqueDao
import com.a00n.machinemanagementroom.data.local.entities.Marque
import com.a00n.machinemanagementroom.data.local.entities.MarqueWithMachines

class MarqueRepository(private val marqueDao: MarqueDao) {
    val marques: LiveData<List<Marque>> = marqueDao.getAll()
    val marquesWithMachines: LiveData<List<MarqueWithMachines>> =
        marqueDao.getAllMarqueWithMachines()

    suspend fun insert(marque: Marque): Int {
        val r = marqueDao.insert(marque)
        return r.toInt()
    }

    suspend fun delete(marque: Marque) {
        marqueDao.delete(marque)
    }

    suspend fun deleteAll() = marqueDao.deleteAll()
    suspend fun update(marque: Marque) {
        marqueDao.update(marque)
    }
}