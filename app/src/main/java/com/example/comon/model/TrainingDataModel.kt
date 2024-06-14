package com.example.comon.model

import com.example.comon.util.TrainingResult
import com.squareup.moshi.Json


data class TrainingBackendResponseDetail(
    //@field:Json(name = "data_id")
    val dataId: String? = null,
//    @field:Json(name = "data")
    val data: String? = null,
//    @field:Json(name = "syllables")
    val syllables: List<String>? = null,
//    @field:Json(name = "phonemes")
    val phonemes: List<String>? = null,
    val phonemeImages: List<String>? = null,
)


data class TrainingBackendResponse(
    //@field:Json(name = "type")
    val type: String? = null,
    //@field:Json(name = "level")
    val level: Int? = null,
    //@field:Json(name = "datas")
    val datas: List<TrainingBackendResponseDetail>? = null
)


data class RecommendBackendResponse(
    val message: String? = null,
    val weak_phoneme: String? = null,
    val score: Float? = null,
    val datas: List<TrainingBackendResponseDetail>? = null
)
data class TrainingModel(
    //@field:Json(name = "type")
    val type: String? = null,
    //@field:Json(name = "level")
    val level: Int? = null
)

data class TrainingResultRequest(val results: List<TrainingResult>)