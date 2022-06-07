package com.skinconnect.doctorapps.data.remote

import android.widget.AutoCompleteTextView
import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import com.skinconnect.doctorapps.data.entity.response.PatientResponse
import com.skinconnect.doctorapps.data.entity.response.ScheduleResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("doctors/schedule")
    suspend fun  addSchedule(
        @Header("Authorization") authorization : String,
        @Part("title") title : AutoCompleteTextView,
        @Part("description") description : RequestBody,
        @Part("time") time : String
    ): AddScheduleResponse

    @GET("doctors/schedule")
    suspend fun  schedule(
        @Header("Authorization") authorization: String): ScheduleResponse

    @GET("users")
    suspend fun getPatient(
        @Header("Authorization") authorization : String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null): PatientResponse

}