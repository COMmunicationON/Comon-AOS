package com.example.comon.network

import android.annotation.SuppressLint
import com.example.comon.model.TrainingBackendResponse
import com.example.comon.model.TrainingModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface TrainingDataService {
    @POST("/training/getData")
    fun postTrainingData(@Body jsonParams: TrainingModel): Call<TrainingBackendResponse>

    companion object {
        private const val CONNECT_TIMEOUT = 30L // in seconds
        private const val READ_TIMEOUT = 30L // in seconds
        private const val BASE_URL =
            "http://ec2-3-39-21-72.ap-northeast-2.compute.amazonaws.com:6000"

        @SuppressLint("SuspiciousIndentation")
        fun getInstance(): TrainingDataService{
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()


                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(TrainingDataService::class.java)

        }
    }
}