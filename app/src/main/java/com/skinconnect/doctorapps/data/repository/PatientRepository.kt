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

    fun getPatient(token : String): LiveData<Result<PatientResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = service.getPatient("Bearer $token")
            emit(Result.Success(client))
        } catch (e : Exception) {
            Log.d("PatientRepository", "patient: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserToken() = preferences.getUserToken()

}