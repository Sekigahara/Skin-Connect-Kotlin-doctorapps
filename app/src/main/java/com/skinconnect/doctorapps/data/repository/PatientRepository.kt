package com.skinconnect.doctorapps.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.skinconnect.doctorapps.data.entity.PatientEntity
import com.skinconnect.doctorapps.data.entity.response.PatientResponse
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.remote.ApiService
import com.skinconnect.doctorapps.data.room.dbpatient.PatientDatabase
import com.skinconnect.doctorapps.ui.main.home.HomePagingSource

class PatientRepository(
    service: ApiService,
    private val preferences: UserPreferences,
    private val patientDatabase : PatientDatabase
) : BaseRepository(service) {

    sealed class Result<out R> private constructor() {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val error: String) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPatient(token : String): LiveData<PagingData<PatientEntity>> {
        wrapEspressoIdlingResource {
            return Pager (
                config = PagingConfig(pageSize = 5),
                remoteMediator = HomePagingSource(patientDatabase, service, token),
                pagingSourceFactory ={ patientDatabase.patientDao().getAllPatient()
                }).liveData
        }
    }

    fun getUserToken() = preferences.getUserToken()

}