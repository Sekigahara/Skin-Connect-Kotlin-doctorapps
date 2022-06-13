package com.skinconnect.doctorapps.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.skinconnect.doctorapps.R
import com.skinconnect.doctorapps.data.entity.response.DoctorDetailsResponse
import com.skinconnect.doctorapps.databinding.FragmentProfileBinding
import com.skinconnect.doctorapps.ui.auth.AuthActivity
import com.skinconnect.doctorapps.ui.auth.AuthViewModel
import com.skinconnect.doctorapps.ui.helper.BaseFragment
import com.skinconnect.doctorapps.ui.helper.ViewModelFactory

class ProfileFragment : BaseFragment() {
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
        val binding = binding as FragmentProfileBinding
        logoutButton = binding.btnLogout
        val doctor = DoctorDetailsResponse(fullName = "", gender = "", address = "", age = 0, weight = 0)
        binding.doctorFullNameTextView.text = doctor.fullName
        binding.doctorGenderTextView.text = doctor.gender
        binding.addressTextView.text = doctor.address
        binding.doctorAgeTextView.text = "${doctor.age}"
        binding.doctorWeightTextView.text = "${doctor.weight}"
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(requireContext())
        val viewModel: AuthViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        logoutButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).setTitle(resources.getString(R.string.logout))
                .setMessage(resources.getString(R.string.are_you_sure))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                    Log.e("TAG", "NOOO")
                    dialog.dismiss()
                }
                .setPositiveButton(R.string.yes) { dialog, which ->
                    Log.e("TAG", "YESSS")
                    dialog.dismiss()
                    val viewModel = viewModel as AuthViewModel
                    viewModel.saveUserToken("")
                    viewModel.saveUserId("")
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }.show()
        }
    }
    companion object {
        const val EXTRA_DOCTOR = "extra_doctor"
    }
}