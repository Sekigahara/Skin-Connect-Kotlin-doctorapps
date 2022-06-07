package com.skinconnect.doctorapps.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skinconnect.doctorapps.data.repository.PatientRepository
import com.skinconnect.doctorapps.data.repository.Result

class HomeViewModel(private val repo: PatientRepository): ViewModel() {

    private val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult

    fun getUserToken() : LiveData<String> {
        return repo.getUserToken().asLiveData()
    }

    fun getPatient(token: String) = repo.getPatient(token)
}