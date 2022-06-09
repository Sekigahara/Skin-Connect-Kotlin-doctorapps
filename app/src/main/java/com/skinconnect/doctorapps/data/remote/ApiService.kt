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
        @Header("Authorization") authorization : String): PatientResponse

}