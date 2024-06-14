package com.example.comon.network

import android.annotation.SuppressLint
import com.example.comon.model.GetPartAverageBackendResponse
import com.example.comon.model.GetTotalCountBackendResponse
import com.example.comon.model.GetWeakPronBackendResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface StaticsDataApiService {
    @GET("/statistics/getTotalCount")
    fun getTotalCount(): Call<GetTotalCountBackendResponse>

    @GET("/weak/weakPron")
    fun getWeakPron(): Call<List<GetWeakPronBackendResponse>>

    @GET("/statistics/getPartAverage")
    fun getPartAverage(): Call<GetPartAverageBackendResponse>

    companion object {
        private const val CONNECT_TIMEOUT = 30L // in seconds
        private const val READ_TIMEOUT = 30L // in seconds
        private const val BASE_URL =
            "http://ec2-43-200-176-219.ap-northeast-2.compute.amazonaws.com:3000"

        @SuppressLint("SuspiciousIndentation")
        fun getInstance(): StaticsDataApiService{
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(StaticsDataApiService::class.java)

        }
    }
}