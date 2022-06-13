package com.skinconnect.doctorapps.data.entity.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class PatientResponse (
    @field:SerializedName("patient")
    val patient: List<PatientDataResponse?>? = null,

    status: String,
    message: String = "Success"
) : BaseResponse(message, status)