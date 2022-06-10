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

    fun getDoctorToken() = repository.getDoctorToken().asLiveData()
    fun getDoctorId() = repository.getDoctorId().asLiveData()
    fun getUserId() = repository.getUserId().asLiveData()

    fun addSchedule(
        doctorId : String,
        userId : String,
        token : String,
        title : AutoCompleteTextView,
        descMedia : RequestBody,
        time : String
    ) : LiveData<ScheduleRepository.Result<AddScheduleResponse>> = repository.addSchedule(doctorId,userId,token,title,descMedia,time)

    fun getSchedule(idDoctor: String, token : String) = repository.getSchedule(idDoctor,token)
}