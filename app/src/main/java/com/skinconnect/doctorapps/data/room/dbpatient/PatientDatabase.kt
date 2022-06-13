package com.skinconnect.doctorapps.data.room.dbpatient

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skinconnect.doctorapps.data.entity.PatientEntity

@Database(
    entities =[PatientEntity::class, PatientKeys::class],
    version = 1,
    exportSchema = false
)

abstract class PatientDatabase : RoomDatabase() {

    abstract fun patientDao() : PatientDao
    abstract fun patientKeysDao(): PatientKeysDao

    companion object {
        @Volatile
        private var INSTANCE: PatientDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): PatientDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PatientDatabase::class.java, "patient_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}