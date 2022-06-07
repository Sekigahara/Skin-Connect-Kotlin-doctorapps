package com.skinconnect.doctorapps.ui.main.home

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.skinconnect.doctorapps.data.entity.PatientEntity
import com.skinconnect.doctorapps.data.remote.ApiService
import com.skinconnect.doctorapps.data.room.dbpatient.PatientDatabase
import com.skinconnect.doctorapps.data.room.dbpatient.PatientKeys


@OptIn(ExperimentalPagingApi::class)
class HomePagingSource(
    private val database: PatientDatabase,
    private val apiService: ApiService,
    private val token: String) : RemoteMediator<Int, PatientEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PatientEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
                val storyKeys = getRemoteKeyClosestToCurrentPosition(state)
                storyKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val storyKeys = getRemoteKeyForFirstItem(state)
                val prevKey = storyKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = storyKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val storyKeys = getRemoteKeyForLastItem(state)
                val nextKey = storyKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = storyKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getPatient("Bearer $token", page, state.config.pageSize)

            val endOfPaginationReached = responseData.listPatient.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.patientKeysDao().deletePatientKeys()
                    database.patientDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.listPatient.map {
                    PatientKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                val stories = responseData.listPatient.map {
                    PatientEntity(it.id, it.name, it.photoUrl)
                }
                database.patientKeysDao().insertAll(keys)
                database.patientDao().insertPatient(stories)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PatientEntity>): PatientKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.patientKeysDao().getPatientKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PatientEntity>): PatientKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.patientKeysDao().getPatientKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PatientEntity>): PatientKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.patientKeysDao().getPatientKeysId(id)
            }
        }
    }

}