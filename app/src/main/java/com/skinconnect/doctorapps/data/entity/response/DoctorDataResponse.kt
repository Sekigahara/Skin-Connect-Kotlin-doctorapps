package com.skinconnect.doctorapps.data.entity.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DoctorDataResponse(
    @field:SerializedName("id_doctor")
    val idDoctor: String,

    @field:SerializedName("details")
    val details: DoctorDetailsResponse
)

@Parcelize
data class DoctorDetailsResponse(
    @field:SerializedName("full_name")
    val fullName: String?,

    @field:SerializedName("address")
    val address: String?,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("age")
    val age: Int
) : Parcelable