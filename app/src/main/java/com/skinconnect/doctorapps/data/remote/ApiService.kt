package com.skinconnect.doctorapps.data.remote

import com.skinconnect.doctorapps.data.entity.AddScheduleRequest
import com.skinconnect.doctorapps.data.entity.LoginRequest
import com.skinconnect.doctorapps.data.entity.RegisterRequest
import com.skinconnect.doctorapps.data.entity.response.*
import retrofit2.http.*

interface ApiService {

    @POST("doctors/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @POST("doctors/register")
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse

    @POST("doctors/{id_doctor}/schedule/{id_user}")
    suspend fun  addSchedule(
        @Path("id_doctor") idDoctor: String,
        @Path("id_user") idUser : String,
        @Header("Authorization") authorization : String,
        @Part("time") time : String,
        @Body request: AddScheduleRequest,
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