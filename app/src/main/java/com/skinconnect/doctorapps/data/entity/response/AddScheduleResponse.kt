package com.skinconnect.doctorapps.data.entity.response

import com.google.gson.annotations.SerializedName

data class AddScheduleResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)