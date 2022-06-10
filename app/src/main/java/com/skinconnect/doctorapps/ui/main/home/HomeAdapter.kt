package com.skinconnect.doctorapps.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skinconnect.doctorapps.data.entity.response.ListPatientItem
import com.skinconnect.doctorapps.databinding.ItemPatientBinding

class HomeAdapter(private val listPatient: ArrayList<ListPatientItem>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {
    inner class ListViewHolder(var binding: ItemPatientBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int) : ListViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ListViewHolder, position : Int) {
        val patient = listPatient[position]
        holder.binding.apply {
            username.text = patient.username
        }
    }

    override fun getItemCount()  : Int = listPatient.size

}