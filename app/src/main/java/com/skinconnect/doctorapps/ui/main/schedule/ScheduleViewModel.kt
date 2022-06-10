package com.skinconnect.doctorapps.ui.main.schedule

import android.widget.AutoCompleteTextView
import androidx.lifecycle.*
import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import com.skinconnect.doctorapps.data.repository.Result
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.ui.helper.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ScheduleViewModel(override val repository : ScheduleRepository): BaseViewModel(repository) {

    fun saveUserToken(token: String) = viewModelScope.launch { repository.saveDoctorToken(token) }
    fun saveUserId(id: String) = viewModelScope.launch { repository.saveDoctorId(id) }
    fun getDoctorId() = repository.getDoctorId().asLiveData()
    fun getDoctorToken() = repository.getDoctorToken().asLiveData()

    fun addSchedule(
        token : String,
        title : AutoCompleteTextView,
        descMedia : RequestBody,
        time : String
    ) : LiveData<ScheduleRepository.Result<AddScheduleResponse>> = repository.addSchedule(token,title,descMedia,time)

    fun getSchedule(token : String) = repository.schedule(token)
}