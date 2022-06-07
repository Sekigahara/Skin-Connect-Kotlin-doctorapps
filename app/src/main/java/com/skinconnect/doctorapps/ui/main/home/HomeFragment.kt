package com.skinconnect.doctorapps.ui.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skinconnect.doctorapps.data.local.UserPreferences
import com.skinconnect.doctorapps.databinding.FragmentHomeBinding
import com.skinconnect.doctorapps.ui.helper.BaseFragment
import com.skinconnect.doctorapps.ui.helper.LoadingStateAdapter
import com.skinconnect.doctorapps.ui.helper.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : BaseFragment() {

    private lateinit var preference: UserPreferences
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var token : String

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
        val factory : ViewModelFactory? = context?.let { ViewModelFactory.getPatientInstance(it) }
        homeViewModel = (factory?.let {
            ViewModelProvider(
                this,
                it
            )
        }?.get(HomeViewModel::class.java) ?: homeViewModel.getUserToken().observe(viewLifecycleOwner){ token ->
            this.token = token
            if (token.isNotEmpty()) {
                val adapter = HomeAdapter()
                binding.rvPatient.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    }
                )
                homeViewModel.getPatient(token).observe(viewLifecycleOwner) { Result ->
                    adapter.submitData(lifecycle, Result)
                }
            }
        }) as HomeViewModel
    }

    override fun setupAction() {    }


}