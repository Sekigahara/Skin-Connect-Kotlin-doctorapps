package com.skinconnect.doctorapps.data.repository

import androidx.lifecycle.MutableLiveData
import com.skinconnect.doctorapps.data.entity.LoginRequest
import com.skinconnect.doctorapps.data.entity.RegisterRequest
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiService

class AuthRepository private constructor(
    service: ApiService,
    private val preferences: UserPreferences,
) : BaseRepository(service) {
    suspend fun login(request: LoginRequest, liveData: MutableLiveData<Result>) =
        wrapEspressoIdlingResource {
            try {
                val response = service.login(request)
                processResponse(response, liveData)
            } catch (exception: Exception) {
                catchError(exception, liveData)
            }
        }

    suspend fun register(request: RegisterRequest, liveData: MutableLiveData<Result>) = try {
        val response = service.register(request)
        processResponse(response, liveData)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

    fun getUserToken() = preferences.getUserToken()

    suspend fun saveUserToken(token: String) = preferences.saveUserToken(token)

    fun getUserId() = preferences.getUserId()

    suspend fun saveUserId(id: String) = preferences.saveUserId(id)

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(service: ApiService, preferences: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(service,
                    preferences)
            }.also { instance = it }
    }
}