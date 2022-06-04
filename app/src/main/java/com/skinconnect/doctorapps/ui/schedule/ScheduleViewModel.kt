package com.skinconnect.doctorapps.ui.schedule

import androidx.lifecycle.*
import com.skinconnect.doctorapps.data.repository.Result
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repo : ScheduleRepository): ViewModel() {
    private val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult

    fun saveUserToken(token: String) = viewModelScope.launch { repo.saveUserToken(token) }

    fun getUserToken() : LiveData<String> {
        return repo.getUserToken().asLiveData()
    }

    fun addSchedule(token : String) = repo.addSchedule(token)
}