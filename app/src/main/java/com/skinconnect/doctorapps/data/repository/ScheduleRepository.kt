package com.skinconnect.doctorapps.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiService
import com.skinconnect.doctorapps.data.room.ScheduleDatabase

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

    fun addSchedule(token : String): LiveData<Result<AddScheduleResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = service.addSchedule("Bearer $token")
            emit(Result.Success(client))
        } catch (e : Exception) {
            Log.d("ScheduleRepository", "addSchedule: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserToken() = preferences.getUserToken()

    suspend fun saveUserToken(token: String) = preferences.saveUserToken(token)

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