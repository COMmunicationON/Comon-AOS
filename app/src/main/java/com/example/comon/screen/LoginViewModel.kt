package com.example.comon.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comon.model.AuthModel
import com.example.comon.model.LoginBackendResponse
import com.example.comon.network.LoginApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(id: String, pw: String) {

        val api = LoginApi.retrofitService()
        val data = AuthModel(null,id, pw)
        Log.d("로그인 진입", data.toString())

        api.postLogin(data).enqueue(object : Callback<LoginBackendResponse> {
            override fun onResponse(
                call: Call<LoginBackendResponse>,
                response: Response<LoginBackendResponse>
            ) {
                Log.d("로그인", "Response code: ${response.code()}")
                Log.d("로그인 통신",response.toString())
                when (response.code()) {
                    200 -> _loginResult.value = LoginResult.Success
                    400 -> _loginResult.value = LoginResult.Failure(400)
                    401 -> _loginResult.value = LoginResult.Failure(401)
                    500 -> _loginResult.value = LoginResult.Failure(500)
                    // 다른 응답 코드에 대한 처리 추가 가능
                    //else -> _loginResult.value = LoginResult.Failure(-1) // 기타 오류
                }
            }

            override fun onFailure(call: Call<LoginBackendResponse>, t: Throwable) {
                if (t is SocketTimeoutException || t is ConnectException) {
                    // Handle timeout or connection errors
                    _loginResult.value = LoginResult.Failure(-2) // Timeout or connection error
                } else {
                    Log.e("로그인 오류", "Unknown error: ${t.message}", t)
                    _loginResult.value = LoginResult.Failure(-1) // Other errors
                }
            }

        })

    }
}

sealed class LoginResult {
    object Success : LoginResult()
    data class Failure(val errorCode: Int) : LoginResult()
}
