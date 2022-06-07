package com.skinconnect.doctorapps.data.room.dbschedule

import androidx.room.*

@Dao
interface ScheduleKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(scheduleKey : List<ScheduleKeys>)

    @Query("DELETE FROM schedule_keys")
    fun deleteScheduleKeys()
}

@Entity(tableName = "schedule_keys")
data class ScheduleKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
