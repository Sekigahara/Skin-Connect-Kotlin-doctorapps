package com.skinconnect.doctorapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiConfig
import com.skinconnect.doctorapps.data.repository.PatientRepository
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.data.room.dbpatient.PatientDatabase
import com.skinconnect.doctorapps.data.room.dbschedule.ScheduleDatabase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

object Injection {
    fun provideRepository(context: Context): ScheduleRepository {
        val service = ApiConfig.getApiService(context)
        val database = ScheduleDatabase.getDatabase(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return ScheduleRepository(service, database, preferences)
    }
    fun providePatient(context: Context): PatientRepository {
        val service = ApiConfig.getApiService(context)
        val database = PatientDatabase.getDatabase(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return PatientRepository(service, preferences, database)
    }
}