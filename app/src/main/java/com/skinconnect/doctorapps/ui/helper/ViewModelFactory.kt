package com.skinconnect.doctorapps.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skinconnect.doctorapps.data.repository.BaseRepository
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.di.ScheduleInject
import com.skinconnect.doctorapps.ui.schedule.ScheduleViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java))
            return ScheduleViewModel(repository as ScheduleRepository) as T

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {

        private var scheduleInstance: ViewModelFactory? = null

        fun getScheduleInstance(context: Context) : ViewModelFactory =
            scheduleInstance ?: synchronized(this) {
                scheduleInstance ?: ViewModelFactory(ScheduleInject.provideRepository(context))
            }.also { scheduleInstance = it }
    }

}
