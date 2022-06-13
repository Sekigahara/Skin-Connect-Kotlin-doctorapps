package com.skinconnect.doctorapps.data.entity.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)
data class PatientDataResponse(
    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("items")
    val items: List<PatientDataItemResponse>,
)

data class PatientDataItemResponse(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("age")
    val age: String,

    @field:SerializedName("_id")
    val id: String,
)