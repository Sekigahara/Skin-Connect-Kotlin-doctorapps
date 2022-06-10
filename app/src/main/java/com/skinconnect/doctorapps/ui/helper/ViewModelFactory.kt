package com.skinconnect.doctorapps.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skinconnect.doctorapps.data.repository.AuthRepository
import com.skinconnect.doctorapps.data.repository.BaseRepository
import com.skinconnect.doctorapps.data.repository.PatientRepository
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.di.Injection
import com.skinconnect.doctorapps.ui.auth.LoginViewModel
import com.skinconnect.doctorapps.ui.auth.RegisterViewModel
import com.skinconnect.doctorapps.ui.main.home.HomeViewModel
import com.skinconnect.doctorapps.ui.main.schedule.ScheduleViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
            return RegisterViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java))
            return ScheduleViewModel(repository as ScheduleRepository) as T
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(repository as PatientRepository) as T

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {

        private var authInstance: ViewModelFactory? = null
        private var scheduleInstance: ViewModelFactory? = null
        private var patientInstance : ViewModelFactory? = null

        fun getAuthInstance(context: Context) : ViewModelFactory {
            if (authInstance == null) {
                val repository = Injection.provideAuthInjection(context)
                authInstance = ViewModelFactory(repository)
            }

            return authInstance as ViewModelFactory
        }

        fun getScheduleInstance(context: Context) : ViewModelFactory =
            scheduleInstance ?: synchronized(this) {
                scheduleInstance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { scheduleInstance = it }

        fun getPatientInstance(context : Context): ViewModelFactory =
            patientInstance ?: synchronized(this){
                scheduleInstance ?: ViewModelFactory(Injection.providePatient(context))
            }.also { patientInstance = it }
    }

}
