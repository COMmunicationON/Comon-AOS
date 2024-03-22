package com.example.comon.model

import com.squareup.moshi.Json

data class AuthModel(
    @field:Json(name = "username") val username: String ?= null,
    @field:Json(name = "userid") val userid: String,
    @field:Json(name = "password") val password: String
    //?= null
)

//백엔드에서 받는 데이터 클래스
data class LoginBackendResponse(
    //val code : String ?= null,
    //200: 성공, 400: 잘못된인증, 401:잘못된요청, 500:서버오류
    @field:Json(name = "message") val message : String ?= null,
    @field:Json(name = "error") val error : String ?= null
)
