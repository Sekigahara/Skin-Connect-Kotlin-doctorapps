package com.skinconnect.doctorapps.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skinconnect.doctorapps.data.entity.PatientEntity
import com.skinconnect.doctorapps.databinding.ItemPatientBinding
import com.skinconnect.doctorapps.ui.main.schedule.NewScheduleFragment

class HomeAdapter : PagingDataAdapter<PatientEntity, HomeAdapter.ListViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int) : ListViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return ListViewHolder(binding)
    }

    class ListViewHolder(private val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context : Context, data : PatientEntity) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.photoUrl)
                    .into(binding.circularAva)

                username.text = data.name
                itemView.setOnClickListener {
                    val intent = Intent(context, NewScheduleFragment::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder : ListViewHolder, position : Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(holder.itemView.context, data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PatientEntity>() {
            override fun areItemsTheSame(oldItem: PatientEntity, newItem: PatientEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PatientEntity, newItem: PatientEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}