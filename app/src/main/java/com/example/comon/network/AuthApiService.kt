package com.example.comon.network

import android.util.Log
import com.example.comon.model.AuthModel
import com.example.comon.model.LoginBackendResponse
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

// Define the timeout durations
private const val CONNECT_TIMEOUT = 30L // in seconds
private const val READ_TIMEOUT = 30L // in seconds
private const val BASE_URL =
    "http://ec2-54-180-125-79.ap-northeast-2.compute.amazonaws.com"

interface LoginApi {
    @POST("/login")
    fun postLogin(@Body jsonParams : AuthModel): Call<LoginBackendResponse>

    companion object {
        fun retrofitService() : LoginApi {
            Log.d("로그인 Api", BASE_URL)

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient) // Set the custom OkHttpClient here
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(LoginApi::class.java)
        }
    }
}

interface SignUpApi {
    @POST("/signup")
    fun postSignUp(@Body jsonParams : AuthModel): Call<LoginBackendResponse>

    companion object {
        fun retrofitService() : SignUpApi {
            Log.d("회원가입 Api", BASE_URL)

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient) // Set the custom OkHttpClient here
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SignUpApi::class.java)
        }
    }
}


