package com.skinconnect.doctorapps.ui.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skinconnect.doctorapps.data.entity.Patient
import com.skinconnect.doctorapps.data.entity.response.PatientResponse
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.repository.Result
import com.skinconnect.doctorapps.databinding.FragmentHomeBinding
import com.skinconnect.doctorapps.ui.helper.BaseFragment
import com.skinconnect.doctorapps.ui.helper.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class HomeFragment : BaseFragment() {

    private lateinit var preference : UserPreferences
    private var idDoctor = ""
    private var token = ""

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding as FragmentHomeBinding
        preference = UserPreferences.getInstance(requireContext().dataStore)

        binding.rvPatient.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getPatientInstance(requireContext())
        val viewModel : HomeViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.getDoctorToken().observe(requireActivity()) {
            token = it
            if (token.isNotBlank() && token.isNotEmpty())
                viewModel.getPatient(idDoctor, token)
        }

        viewModel.getDoctorId().observe(requireActivity()) {
            idDoctor = it
            if (idDoctor.isNotBlank() && idDoctor.isNotEmpty())
                viewModel.getPatient(idDoctor, token)
        }
        viewModel.getPatientResult.observe(requireActivity()) {
            observeGetPatient(it)
        }
    }

        private fun observeGetPatient(result : Result?){
            if (result == null) return
            val binding = binding as FragmentHomeBinding

            when (result) {
                Result.Loading-> binding.progressBar.visibility = View.VISIBLE
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Failure : " + result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    val patientDetailList = (result.data as PatientResponse).patient
                    val patientList = mutableListOf<Patient>()

                    patientDetailList?.forEach { patientData ->
                        patientData?.items?.forEach { patientItem ->
                            val patient = Patient(
                                patientItem.name, patientItem.age
                            )

                            patientList.add(patient)
                        }
                    }

                    val listPatientAdapter = HomeAdapter(patientList)
                    binding.rvPatient.adapter = listPatientAdapter
                }
            }
        }

    override fun setupAction() {}
}
