package com.example.comon.model

data class GetTotalCountBackendResponse(
    val totalCount: Int? = null,
    val totalProblemNum: Int? = null,
    val percentage: Float? = null
)

data class GetWeakPronBackendResponse(
    val phoneme: String? = null,
    val weakPercentage: Int? = null
)

data class GetPartAverageBackendResponse(
    val syllable_average: Float? = null,
    val word_average: Float? = null,
    val sentence_average: Float? = null
)