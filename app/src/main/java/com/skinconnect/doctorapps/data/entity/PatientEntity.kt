package com.skinconnect.doctorapps.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "patient")
data class PatientEntity (
    @PrimaryKey
    val idUser : String,
    val name : String,
    @ColumnInfo(name = "photo_url")
    val photoUrl : String
        ):Parcelable