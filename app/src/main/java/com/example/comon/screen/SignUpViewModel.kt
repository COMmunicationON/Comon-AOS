package com.example.comon.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comon.model.AuthModel
import com.example.comon.model.LoginBackendResponse
import com.example.comon.network.SignUpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class SignUpViewModel : ViewModel() {

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> = _signUpResult

    fun signUp(username: String, userid: String, password: String) {
        val api = SignUpApi.retrofitService()
        val data = AuthModel(username, userid, password)

        api.postSignUp(data).enqueue(object : Callback<LoginBackendResponse> {
            override fun onResponse(
                call: Call<LoginBackendResponse>,
                response: Response<LoginBackendResponse>
            ) {
                Log.d("SignUpViewModel", "Response code: ${response.code()}")
                when (response.code()) {
                    201 -> _signUpResult.value = SignUpResult.Success
                    400 -> _signUpResult.value = SignUpResult.Failure(400)
                    409 -> _signUpResult.value = SignUpResult.Failure(409)
                    500 -> _signUpResult.value = SignUpResult.Failure(500)
                    else -> _signUpResult.value = SignUpResult.Failure(-1) // 기타 오류
                }
            }

            override fun onFailure(call: Call<LoginBackendResponse>, t: Throwable) {
                if (t is SocketTimeoutException || t is ConnectException) {
                    _signUpResult.value = SignUpResult.Failure(-2) // Timeout or connection error
                } else {
                    Log.e("SignUpViewModel", "Unknown error: ${t.message}")
                    _signUpResult.value = SignUpResult.Failure(-1) // Other errors
                }
            }
        })
    }
}

sealed class SignUpResult {
    object Success : SignUpResult()
    data class Failure(val errorCode: Int) : SignUpResult()
}