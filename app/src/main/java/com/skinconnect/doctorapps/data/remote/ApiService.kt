package com.skinconnect.doctorapps.data.remote

import android.widget.AutoCompleteTextView
import com.skinconnect.doctorapps.data.entity.LoginRequest
import com.skinconnect.doctorapps.data.entity.RegisterRequest
import com.skinconnect.doctorapps.data.entity.response.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("doctors/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @POST("doctors/register")
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse

    @POST("doctors/{doctorId}/schedule/{userId}")
    suspend fun  addSchedule(
        @Path("doctorId") doctorId: String,
        @Path("userId") userId : String,
        @Header("Authorization") authorization : String,
        @Part("title") title : AutoCompleteTextView,
        @Part("description") description : RequestBody,
        @Part("time") time : String
    ): AddScheduleResponse

    @GET("doctors/{id}/schedule")
    suspend fun  getSchedule(
        @Path("id") id: String,
        @Header("Authorization") authorization: String): ScheduleResponse

    @GET("doctors/{id}/patient")
    suspend fun getPatient(
        @Path("id") id: String,
        @Header("Authorization") authorization : String): PatientResponse

}