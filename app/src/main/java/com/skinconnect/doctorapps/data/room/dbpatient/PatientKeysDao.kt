package com.skinconnect.doctorapps.data.room.dbpatient

import androidx.room.*

@Dao
interface PatientKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(patientKey : List<PatientKeys>)

    @Query("SELECT * FROM patient_keys WHERE id = :id")
    suspend fun getPatientKeysId(id: String): PatientKeys?

    @Query("DELETE FROM patient_keys")
    fun deletePatientKeys()
}

@Entity(tableName = "patient_keys")
data class PatientKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)