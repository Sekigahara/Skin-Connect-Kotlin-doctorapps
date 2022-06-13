package com.skinconnect.doctorapps.ui.main.home

import androidx.lifecycle.*
import com.skinconnect.doctorapps.data.entity.Patient
import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import com.skinconnect.doctorapps.data.entity.response.PatientResponse
import com.skinconnect.doctorapps.data.repository.PatientRepository
import com.skinconnect.doctorapps.data.repository.Result
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.ui.helper.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(override val repository: PatientRepository): BaseViewModel(repository) {

    fun saveUserToken(token: String) = viewModelScope.launch { repository.saveDoctorToken(token) }
    fun saveUserId(id: String) = viewModelScope.launch { repository.saveDoctorId(id) }
    fun getDoctorId() = repository.getDoctorId().asLiveData()
    fun getDoctorToken() = repository.getDoctorToken().asLiveData()

    private val _getPatientResult = MutableLiveData<Result>()
    val getPatientResult : LiveData<Result> = _getPatientResult

    fun getPatient(idUser: String, token : String
    ): LiveData<PatientRepository.Result<PatientResponse>> = repository.getPatient(idUser, token)
}