package com.example.comon.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comon.model.GetPartAverageBackendResponse
import com.example.comon.model.GetTotalCountBackendResponse
import com.example.comon.model.GetWeakPronBackendResponse
import com.example.comon.model.RecommendBackendResponse
import com.example.comon.model.TrainingBackendResponse
import com.example.comon.model.TrainingModel
import com.example.comon.network.StaticsDataApiService
import com.example.comon.network.TrainingDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class HomeViewModel : ViewModel() {
    private val _trainingData = MutableLiveData<RecommendBackendResponse>()
    val trainingData: LiveData<RecommendBackendResponse> = _trainingData

    private val _totalCountData = MutableLiveData<GetTotalCountBackendResponse>()
    val totalCountData: LiveData<GetTotalCountBackendResponse> = _totalCountData

    private val _weakPronData = MutableLiveData<List<GetWeakPronBackendResponse>>()
    val weakPronData: LiveData<List<GetWeakPronBackendResponse>> = _weakPronData

    private val _partAverage = MutableLiveData<GetPartAverageBackendResponse>()
    val partAverage: LiveData<GetPartAverageBackendResponse> = _partAverage

    fun fetchRecommendData() {
        val api = TrainingDataService.getInstance()

        //api.getTrainingData(trainingModel).enqueue(object : Callback<TrainingBackendResponse> {
        api.getRecommendData().enqueue(object : Callback<RecommendBackendResponse> {
            override fun onResponse(
                call: Call<RecommendBackendResponse>,
                response: Response<RecommendBackendResponse>
            ) {
                if (response.isSuccessful) {
                    _trainingData.value = response.body()
                    Log.d("TrainingViewModel!!", "Successful response: ${_trainingData.value}")
                } else {
                    Log.e("TrainingViewModel", "Unsuccessful response: ${response.code()}")
                    // Handle unsuccessful response here
                }
            }

            override fun onFailure(call: Call<RecommendBackendResponse>, t: Throwable) {
                if (t is SocketTimeoutException || t is ConnectException) {
                    Log.e("TrainingViewModel", "Timeout or connection error: ${t.message}")
                    // Handle timeout or connection error here
                } else {
                    Log.e("TrainingViewModel", "Unknown error: ${t.message}")
                    // Handle other errors here
                }
            }
        })
    }

    fun fetchTotalCount() {
        val api = StaticsDataApiService.getInstance()

        api.getTotalCount().enqueue(object : Callback<GetTotalCountBackendResponse> {
            override fun onResponse(
                call: Call<GetTotalCountBackendResponse>,
                response: Response<GetTotalCountBackendResponse>
            ) {
                if (response.isSuccessful) {
                    _totalCountData.value=response.body()
                    // Handle successful response here
                    // Update LiveData with the response body
                } else {
                    // Handle unsuccessful response here
                }
            }

            override fun onFailure(call: Call<GetTotalCountBackendResponse>, t: Throwable) {
                // Handle failure here
            }
        })
    }

    fun fetchWeakPron() {
        val api = StaticsDataApiService.getInstance()
        api.getWeakPron().enqueue(object : Callback<List<GetWeakPronBackendResponse>> {
            override fun onResponse(
                call: Call<List<GetWeakPronBackendResponse>>,
                response: Response<List<GetWeakPronBackendResponse>>
            ) {
                if (response.isSuccessful) {
                    _weakPronData.value=response.body()
                    // Handle successful response here
                    // Update LiveData with the response body
                } else {
                    // Handle unsuccessful response here
                }
            }

            override fun onFailure(call: Call<List<GetWeakPronBackendResponse>>, t: Throwable) {
                // Handle failure here
            }
        })
    }

    fun fetchPartAverage() {
        val api = StaticsDataApiService.getInstance()
        api.getPartAverage().enqueue(object : Callback<GetPartAverageBackendResponse> {
            override fun onResponse(
                call: Call<GetPartAverageBackendResponse>,
                response: Response<GetPartAverageBackendResponse>
            ) {
                if (response.isSuccessful) {
                    _partAverage.value=response.body()
                    // Handle successful response here
                    // Update LiveData with the response body
                } else {
                    // Handle unsuccessful response here
                }
            }

            override fun onFailure(call: Call<GetPartAverageBackendResponse>, t: Throwable) {
                // Handle failure here
            }
        })
    }


}