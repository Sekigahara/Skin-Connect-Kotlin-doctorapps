package com.skinconnect.doctorapps.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skinconnect.doctorapps.data.entity.ScheduleEntity

@Database(
    entities =[ScheduleEntity::class, ScheduleKeys::class],
    version = 1,
    exportSchema = false
)

abstract class ScheduleDatabase : RoomDatabase() {

    abstract fun scheduleDao() : ScheduleDao
    abstract fun scheduleKeysDao(): ScheduleKeysDao

    companion object {
        @Volatile
        private var INSTANCE: ScheduleDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ScheduleDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleDatabase::class.java, "schedule_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}