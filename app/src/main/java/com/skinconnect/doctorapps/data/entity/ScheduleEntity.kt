package com.skinconnect.doctorapps.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "schedule")
data class ScheduleEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String
        ):Parcelable