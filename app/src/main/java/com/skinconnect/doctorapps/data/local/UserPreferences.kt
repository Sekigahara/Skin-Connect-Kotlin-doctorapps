package com.skinconnect.doctorapps.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val doctorToken = stringPreferencesKey("token_key")
    private val doctorId = stringPreferencesKey("id_key")

    fun getDoctorToken() = dataStore.data.map { it[doctorToken] ?: "" }

    suspend fun saveDoctorToken(token: String) = dataStore.edit { it[doctorToken] = token }

    fun getDoctorId() = dataStore.data.map { it[doctorId] ?: "" }

    suspend fun saveDoctorId(id: String) = dataStore.edit { it[doctorId] = id }

    companion object {
        @Volatile
        private var instance: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) = instance ?: synchronized(this) {
            instance ?: UserPreferences(dataStore)
        }.also { instance = it }
    }
}