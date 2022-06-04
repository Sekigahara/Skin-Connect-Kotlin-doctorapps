package com.skinconnect.doctorapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiConfig
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.data.room.ScheduleDatabase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

object ScheduleInject {
    fun provideRepository(context: Context): ScheduleRepository {
        val service = ApiConfig.getApiService(context)
        val database = ScheduleDatabase.getDatabase(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return ScheduleRepository(service, database, preferences)
    }
}