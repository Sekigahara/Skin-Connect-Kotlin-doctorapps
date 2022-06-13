package com.skinconnect.doctorapps.data.repository

import android.util.Log
import android.widget.AutoCompleteTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.skinconnect.doctorapps.data.entity.AddScheduleRequest
import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import com.skinconnect.doctorapps.data.entity.response.ScheduleResponse
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiService
import com.skinconnect.doctorapps.data.room.dbschedule.ScheduleDatabase
import okhttp3.RequestBody

class ScheduleRepository(
    service: ApiService,
    private val scheduleDatabase : ScheduleDatabase,
    private val preferences : UserPreferences,
) : BaseRepository(service) {

    sealed class Result<out R> private constructor() {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val error: String) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    fun addSchedule(idDoctor: String, idUser : String,  token : String,time : String, request : AddScheduleRequest): LiveData<Result<AddScheduleResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = service.addSchedule(idDoctor, idUser,"Bearer $token", time, request)
            emit(Result.Success(client))
        } catch (e : Exception) {
            Log.d("ScheduleRepository", "addSchedule: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getSchedule(idDoctor: String, token : String): LiveData<Result<ScheduleResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = service.getSchedule(idDoctor,"Bearer $token")
            emit(Result.Success(client))
        } catch (e : Exception) {
            Log.d("ScheduleRepository", "schedule: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDoctorToken() = preferences.getDoctorToken()

    fun getUserId() = preferences.getUserId()

    suspend fun saveUserId(id: String) = preferences.saveUserId(id)

    suspend fun saveDoctorToken(token: String) = preferences.saveDoctorToken(token)

    fun getDoctorId() = preferences.getDoctorId()

    suspend fun saveDoctorId(id: String) = preferences.saveDoctorId(id)

    companion object {
        @Volatile
        private var instance: ScheduleRepository? = null

        fun getInstance(service: ApiService, scheduleDatabase : ScheduleDatabase, preferences: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: ScheduleRepository(service,scheduleDatabase,
                    preferences)
            }.also { instance = it }
    }
}