package com.skinconnect.doctorapps.ui.schedule

import android.widget.AutoCompleteTextView
import androidx.lifecycle.*
import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import com.skinconnect.doctorapps.data.repository.Result
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ScheduleViewModel(private val repo : ScheduleRepository): ViewModel() {
    private val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult

    fun saveUserToken(token: String) = viewModelScope.launch { repo.saveUserToken(token) }

    fun getUserToken() : LiveData<String> {
        return repo.getUserToken().asLiveData()
    }

    fun addSchedule(
        token : String,
        title : AutoCompleteTextView,
        descMedia : RequestBody,
        time : String
    ) : LiveData<ScheduleRepository.Result<AddScheduleResponse>> = repo.addSchedule(token,title,descMedia,time)

    fun getSchedule(token : String) = repo.schedule(token)
}