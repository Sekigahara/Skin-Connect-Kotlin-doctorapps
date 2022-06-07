package com.skinconnect.doctorapps.data.remote

import android.content.Context
import android.content.pm.PackageManager
import com.skinconnect.doctorapps.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    lateinit var baseUrl: String

    fun getApiService(context: Context, isForMl: Boolean = false): ApiService {
        val loggingInterceptor =
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .apply {
                baseUrl = metaData.getString(if (isForMl) "BASE_URL_ML" else "BASE_URL").toString()
            }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()

        return retrofit.create(ApiService::class.java)
    }
}