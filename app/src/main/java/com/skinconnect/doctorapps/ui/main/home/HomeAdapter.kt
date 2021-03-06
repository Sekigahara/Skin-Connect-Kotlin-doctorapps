package com.skinconnect.doctorapps.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skinconnect.doctorapps.data.entity.Patient
import com.skinconnect.doctorapps.databinding.ItemPatientBinding

class HomeAdapter(private val listPatient: List<Patient>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {
    inner class ListViewHolder(var binding: ItemPatientBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int) : ListViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ListViewHolder, position : Int) {
        val patient = listPatient[position]
        holder.binding.apply {
            tvName.text = patient.name
            tvAge.text = patient.age
        }
    }

    override fun getItemCount()  : Int = listPatient.size

}