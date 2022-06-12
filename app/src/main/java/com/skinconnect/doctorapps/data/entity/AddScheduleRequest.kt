package com.skinconnect.doctorapps.data.entity

import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody

data class AddScheduleRequest(
    @field:SerializedName("id_doctor")
    val idDoctor : String,

    @field:SerializedName("id_user")
    val idUser : String,

    @field:SerializedName("title")
    val title : String?,

    @field:SerializedName("desc_media")
    val descMedia : RequestBody,

    @field:SerializedName("time")
    val time : String
)
