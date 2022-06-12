package com.skinconnect.doctorapps.ui.main.schedule

import android.widget.AutoCompleteTextView
import androidx.lifecycle.*
import com.skinconnect.doctorapps.data.entity.AddScheduleRequest
import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import com.skinconnect.doctorapps.data.entity.response.BaseResponse
import com.skinconnect.doctorapps.data.repository.Result
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.ui.helper.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ScheduleViewModel(override val repository : ScheduleRepository): BaseViewModel(repository) {

    private val _addScheduleResult = MutableLiveData<Result>()
    val addScheduleResult: LiveData<Result> = _addScheduleResult

    fun getDoctorToken() = repository.getDoctorToken().asLiveData()
    fun getDoctorId() = repository.getDoctorId().asLiveData()
    fun getUserId() = repository.getUserId().asLiveData()

    fun addSchedule(
        idDoctor : String,
        idUser : String,
        token : String,
        time : String,
        request : AddScheduleRequest
    ): LiveData<ScheduleRepository.Result<AddScheduleResponse>> = repository.addSchedule(idDoctor, idUser, token, time, request)

    fun getSchedule(idDoctor: String, token : String) = repository.getSchedule(idDoctor,token)
}