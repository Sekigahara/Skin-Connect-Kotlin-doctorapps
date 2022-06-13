package com.skinconnect.doctorapps.data.room.dbpatient

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skinconnect.doctorapps.data.entity.PatientEntity

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: List<PatientEntity>)

    @Query("SELECT * FROM patient")
    fun getAllPatient(): PagingSource<Int, PatientEntity>

    @Query("DELETE FROM patient")
    fun deleteAll()
}