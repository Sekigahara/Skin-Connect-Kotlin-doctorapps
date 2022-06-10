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
import com.skinconnect.doctorapps.data.entity.response.ListPatientItem
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.repository.PatientRepository
import com.skinconnect.doctorapps.databinding.FragmentHomeBinding
import com.skinconnect.doctorapps.ui.helper.BaseFragment
import com.skinconnect.doctorapps.ui.helper.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class HomeFragment : BaseFragment() {

    private lateinit var preference: UserPreferences

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
       viewBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        val binding = binding as FragmentHomeBinding
        val factory = ViewModelFactory.getPatientInstance(requireContext())
        val viewModel: HomeViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.getDoctorToken().observe(viewLifecycleOwner){ token ->
            if (token.isNotEmpty()){
                viewModel.getPatient(token).observe(viewLifecycleOwner){result ->
                    if (result != null){
                        when(result) {
                            is PatientRepository.Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is PatientRepository.Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val schedule = result.data.listPatient
                                val listPatientAdapter = HomeAdapter(schedule as ArrayList<ListPatientItem>)
                                binding.rvPatient.adapter = listPatientAdapter
                            }
                            is PatientRepository.Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    context,
                                    "Failure : " + result.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setupAction() {    }


}