package com.skinconnect.doctorapps.ui.schedule

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.skinconnect.doctorapps.R
import com.skinconnect.doctorapps.databinding.FragmentScheduleBinding
import com.skinconnect.doctorapps.ui.helper.BaseFragment
import com.skinconnect.doctorapps.ui.helper.NoFilterArrayAdapter
import java.util.*


class ScheduleFragment : BaseFragment() {

    private lateinit var titleAutoCompleteTextView : AutoCompleteTextView
    private lateinit var descriptionText : EditText
    private lateinit var timeStart : TextView
    private lateinit var timeEnd : TextView
    private lateinit var sendButton : Button

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    override fun setupView() {
        val binding = binding as FragmentScheduleBinding
        titleAutoCompleteTextView = binding.autoCompleteTextViewSchedule
        val adapter = NoFilterArrayAdapter(requireContext(),
        R.layout.list_item_dropdown,
        arrayOf("Medicine","Rest","Consult","Treatment"))
        titleAutoCompleteTextView.setAdapter(adapter)
        descriptionText = binding.cvDescription
        sendButton = binding.btnSend
        timeStart = binding.cvTimeStart
        timeEnd = binding.cvTimeEnd

    }

    @SuppressLint("SetTextI18n")
    override fun setupAction() {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val min = cal.get(Calendar.MINUTE)

        timeStart.setOnClickListener{
            val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                var setTime = ""
                var h = 0
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
            val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener{ _, hourOfDay, minute ->
                var setTime = ""
                var h = 0
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

    override fun setupViewModel() {    }

}