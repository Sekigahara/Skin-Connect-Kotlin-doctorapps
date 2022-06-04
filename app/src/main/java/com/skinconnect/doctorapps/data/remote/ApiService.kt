package com.skinconnect.doctorapps.data.remote

import com.skinconnect.doctorapps.data.entity.response.AddScheduleResponse
import retrofit2.http.*

interface ApiService {

    @POST("doctors/schedule")
    suspend fun  addSchedule(
        @Header("Authorization") authorization: String): AddScheduleResponse

}