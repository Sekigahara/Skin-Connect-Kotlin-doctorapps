package com.skinconnect.doctorapps.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.skinconnect.doctorapps.data.entity.response.PatientResponse
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiService

class PatientRepository(
    service: ApiService,
    private val preferences: UserPreferences
) : BaseRepository(service) {

    sealed class Result<out R> private constructor() {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val error: String) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    fun getPatient(idDoctor: String, token : String): LiveData<Result<PatientResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = service.getPatient(idDoctor,"Bearer $token")
            emit(Result.Success(client))
        } catch (e : Exception) {
            Log.d("PatientRepository", "patient: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDoctorToken() = preferences.getDoctorToken()
    suspend fun saveDoctorToken(token: String) = preferences.saveDoctorToken(token)

    fun getDoctorId() = preferences.getDoctorId()
    suspend fun saveDoctorId(id: String) = preferences.saveDoctorId(id)

    companion object {
        @Volatile
        private var instance: PatientRepository? = null

        fun getInstance(service: ApiService, preferences: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: PatientRepository(service,
                    preferences)
            }.also { instance = it }
    }

}