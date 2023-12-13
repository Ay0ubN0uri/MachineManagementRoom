package com.a00n.machinemanagementroom.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a00n.machinemanagementroom.data.local.MarqueMachineDatabase
import com.a00n.machinemanagementroom.data.local.dao.MarqueDao
import com.a00n.machinemanagementroom.data.local.entities.Machine
import com.a00n.machinemanagementroom.data.local.entities.MachineWithMarque
import com.a00n.machinemanagementroom.data.local.entities.Marque
import com.a00n.machinemanagementroom.data.local.entities.MarqueWithMachineCount
import com.a00n.machinemanagementroom.data.local.entities.MarqueWithMachines
import com.a00n.machinemanagementroom.data.repository.MachineRepository
import com.a00n.machinemanagementroom.data.repository.MarqueRepository
import kotlinx.coroutines.launch


sealed class InsertResult {
    data class Success(val insertedMarqueId: Int) : InsertResult()
    object Error : InsertResult()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var marqueDao: MarqueDao

    private var marqueRepository: MarqueRepository
    private var machineRepository: MachineRepository
    var marques: LiveData<List<Marque>>
    var machines: LiveData<List<Machine>>
    var marquesWithMachines: LiveData<List<MarqueWithMachines>>
    var machinesWithMarque: LiveData<List<MachineWithMarque>>
    var marquesWithMachineCount: LiveData<List<MarqueWithMachineCount>>

    init {
        marqueDao =
            MarqueMachineDatabase.getDatabase(context = application.applicationContext).marqueDao
        marqueRepository = MarqueRepository(marqueDao)
        machineRepository =
            MachineRepository(MarqueMachineDatabase.getDatabase(context = application.applicationContext).machineDao)
        marques = marqueRepository.marques
        machines = machineRepository.machines
        marquesWithMachines = marqueRepository.marquesWithMachines
        machinesWithMarque = machineRepository.machinesWithMarque
        marquesWithMachineCount = machineRepository.marquesWithMachineCount
    }

    fun insertMarque(marque: Marque, onResult: (InsertResult) -> Unit = {}) {
        viewModelScope.launch {
            val result = marqueRepository.insert(marque)
            Log.i("info", "insertMarque: this is the r $result")
            if (result > 0) {
                onResult(InsertResult.Success(result))
            } else {
                onResult(InsertResult.Error)
            }
        }
    }

    fun insertMachine(machine: Machine) {
        viewModelScope.launch {
            machineRepository.insert(machine)
        }
    }

    fun deleteMarque(marque: Marque) {
        viewModelScope.launch {
            marqueRepository.delete(marque)
        }
    }

    fun deleteMachine(machine: Machine) {
        viewModelScope.launch {
            machineRepository.delete(machine)
        }
    }

    fun updateMachine(machine: Machine) {
        viewModelScope.launch {
            machineRepository.update(machine)
        }
    }

    fun updateMarque(marque: Marque) {
        viewModelScope.launch {
            marqueRepository.update(marque)
        }
    }


//    fun getMarquesWithMachineCount(){
//        viewModelScope.launch {
//            val marquesWithMachineCount = machineRepository.getMarquesWithMachineCount()
//            // Now, marquesWithMachineCount contains the list of MarqueWithMachineCount objects
//        }
//    }
}