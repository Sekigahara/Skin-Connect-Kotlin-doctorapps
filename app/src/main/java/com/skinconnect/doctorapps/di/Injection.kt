package com.skinconnect.doctorapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiConfig
import com.skinconnect.doctorapps.data.repository.AuthRepository
import com.skinconnect.doctorapps.data.repository.PatientRepository
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.data.room.dbschedule.ScheduleDatabase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

object Injection {

    fun provideAuthInjection(context: Context): AuthRepository {
        val service = ApiConfig.getApiService(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return AuthRepository.getInstance(service, preferences)
    }

    fun provideRepository(context: Context): ScheduleRepository {
        val service = ApiConfig.getApiService(context)
        val database = ScheduleDatabase.getDatabase(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return ScheduleRepository.getInstance(service, database, preferences)
    }
    fun providePatient(context: Context): PatientRepository {
        val service = ApiConfig.getApiService(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return PatientRepository.getInstance(service, preferences)
    }
}