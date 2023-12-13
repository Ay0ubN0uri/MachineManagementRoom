package com.a00n.machinemanagementroom.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a00n.machinemanagementroom.data.local.dao.MachineDao
import com.a00n.machinemanagementroom.data.local.dao.MarqueDao
import com.a00n.machinemanagementroom.data.local.entities.Machine
import com.a00n.machinemanagementroom.data.local.entities.Marque

@Database(entities = [Marque::class, Machine::class], version = 1, exportSchema = false)
abstract class MarqueMachineDatabase : RoomDatabase() {

    abstract val marqueDao: MarqueDao
    abstract val machineDao: MachineDao

    companion object {
        @Volatile
        private var INSTANCE: MarqueMachineDatabase? = null

        fun getDatabase(context: Context): MarqueMachineDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MarqueMachineDatabase::class.java,
                        "machines_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
                return instance
            }
        }

    }
}