package com.skinconnect.doctorapps.ui.main.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skinconnect.doctorapps.data.entity.response.ListScheduleItem
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.databinding.FragmentScheduleBinding
import com.skinconnect.doctorapps.ui.helper.BaseFragment
import com.skinconnect.doctorapps.ui.helper.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class ScheduleFragment : BaseFragment() {

    private lateinit var _username : String
    private lateinit var _tvDate : String
    private lateinit var _backButton : ImageButton
    private lateinit var preference: UserPreferences
    private var idDoctor = ""

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding as FragmentScheduleBinding
        preference = UserPreferences.getInstance(requireContext().dataStore)

        binding.rvSchedule.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
        val binding = binding as FragmentScheduleBinding
        _username = binding.tvGreetingUser.toString()
        _tvDate = binding.tvDate.toString()
        _backButton = binding.buttonBackSchedule
    }

    override fun setupViewModel() {
        val binding = binding as FragmentScheduleBinding
        val factory = ViewModelFactory.getScheduleInstance(requireContext())
        val viewModel: ScheduleViewModel by viewModels { factory }
        this.viewModel = viewModel
        this.idDoctor = viewModel.getDoctorId().toString()

        viewModel.getDoctorToken().observe(viewLifecycleOwner){ token ->
            if (token.isNotEmpty()){
                viewModel.getSchedule(idDoctor,token).observe(viewLifecycleOwner){ result ->
                    if (result != null){
                        when(result) {
                            is ScheduleRepository.Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is ScheduleRepository.Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val schedule = result.data.listSchedule
                                val listScheduleAdapter = ScheduleAdapter(schedule as ArrayList<ListScheduleItem>)
                                binding.rvSchedule.adapter = listScheduleAdapter
                            }
                            is ScheduleRepository.Result.Error -> {
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

    override fun setupAction() {
       // val binding = binding as FragmentScheduleBinding
    }
}