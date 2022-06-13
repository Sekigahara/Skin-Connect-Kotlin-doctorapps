package com.skinconnect.doctorapps.ui.main.schedule

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.skinconnect.doctorapps.R
import com.skinconnect.doctorapps.data.entity.AddScheduleRequest
import com.skinconnect.doctorapps.data.repository.ScheduleRepository
import com.skinconnect.doctorapps.databinding.FragmentNewScheduleBinding
import com.skinconnect.doctorapps.ui.helper.BaseFragment
import com.skinconnect.doctorapps.ui.helper.NoFilterArrayAdapter
import com.skinconnect.doctorapps.ui.helper.ViewModelFactory
import java.util.*


class NewScheduleFragment : BaseFragment() {

    private lateinit var scheduleViewModel : ScheduleViewModel
    private lateinit var titleAutoCompleteTextView : AutoCompleteTextView
    private lateinit var descriptionText : EditText
    private lateinit var timeStart : TextView
    private lateinit var timeEnd : TextView
    private lateinit var sendButton : Button
    private lateinit var token : String
    private lateinit var addScheduleRequest : AddScheduleRequest
    private var idDoctor = ""
    private var idUser = ""

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentNewScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    override fun setupView() {
        val binding = binding as FragmentNewScheduleBinding
        titleAutoCompleteTextView = binding.autoCompleteTextViewSchedule
        val adapter = NoFilterArrayAdapter(requireContext(),
            R.layout.list_item_dropdown,
            arrayOf("Medicine","Rest","Consult","Treatment"))
        titleAutoCompleteTextView.setAdapter(adapter)
        descriptionText = binding.cvDescription
        sendButton = binding.btnSend
        timeStart = binding.cvTimeStart
        timeEnd = binding.cvTimeEnd

        sendButton.setOnClickListener {
            sendSchedule()
        }

    }
    override fun setupViewModel() {
        val factory : ViewModelFactory = ViewModelFactory.getScheduleInstance(requireContext())
        scheduleViewModel =ViewModelProvider(this,factory)[ScheduleViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun setupAction() {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val min = cal.get(Calendar.MINUTE)

        timeStart.setOnClickListener{
            val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                val setTime : String
                val h : Int
                if(hourOfDay>12){
                    h = hourOfDay-12
                    setTime = "PM"
                }else{
                    h = hourOfDay
                    setTime = "AM"
                }
                timeStart.text = "$h:$minute $setTime"
            },hour,min,true)
            timePickerDialog.show()
        }
        timeEnd.setOnClickListener{
            val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                val setTime : String
                val h : Int
                if(hourOfDay>12){
                    h = hourOfDay-12
                    setTime = "PM"
                }else{
                    h = hourOfDay
                    setTime = "AM"
                }
                timeEnd.text = "$h:$minute $setTime"
            },hour,min,true)
            timePickerDialog.show()
        }
    }
    private fun sendSchedule() {
        val binding = binding as FragmentNewScheduleBinding
        val viewModel: ScheduleViewModel by viewModels()
        this.viewModel = viewModel
        val description = binding.cvDescription.text.toString().trim()
        val time = "$timeStart - $timeEnd"

        if (description.isEmpty()) {
            binding.cvDescription.error =
                resources.getString(R.string.message_validation, "description")
        } else {
            showLoading(true)

            scheduleViewModel.addSchedule(idDoctor, idUser, token, time, addScheduleRequest).observe(requireActivity()) { result ->
                if (result != null){
                    when(result) {
                        is ScheduleRepository.Result.Loading -> {
                            showLoading(true)
                        }
                        is ScheduleRepository.Result.Success -> {
                            showLoading(false)
                            Toast.makeText(context, R.string.message_validation, Toast.LENGTH_SHORT).show()
                        }
                        is ScheduleRepository.Result.Error -> {
                            showLoading(false)
                            Toast.makeText(context, "Failure : " + result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        val binding = binding as FragmentNewScheduleBinding
        binding.apply {
            sendButton.isEnabled = !isLoading
            cvTimeStart.isEnabled = !isLoading
            cvTimeEnd.isEnabled = !isLoading
            cvDescription.isEnabled = !isLoading

            if (isLoading) {
                progressBar.animateVisibility(true)
            } else {
                progressBar.animateVisibility(false)
            }
        }
    }
    private fun View.animateVisibility(isVisible: Boolean, duration: Long = 400) {
        ObjectAnimator
            .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
            .setDuration(duration)
            .start()
    }
}