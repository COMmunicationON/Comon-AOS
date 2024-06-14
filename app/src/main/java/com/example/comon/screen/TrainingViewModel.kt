package com.example.comon.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comon.model.TrainingBackendResponse
import com.example.comon.model.TrainingModel
import com.example.comon.model.TrainingResultRequest
import com.example.comon.network.TrainingDataService
import com.example.comon.util.TrainingResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class TrainingViewModel : ViewModel() {
    //전체, 단어, 음절, 음소

    private val _trainingData = MutableLiveData<TrainingBackendResponse>()
    val trainingData: LiveData<TrainingBackendResponse> = _trainingData

    fun fetchTrainingData(trainingModel: TrainingModel) {
        val api = TrainingDataService.getInstance()

        //api.getTrainingData(trainingModel).enqueue(object : Callback<TrainingBackendResponse> {
        api.getTrainingData(trainingModel.type ?: "word", trainingModel.level?: 1).enqueue(object : Callback<TrainingBackendResponse> {
            override fun onResponse(
                call: Call<TrainingBackendResponse>,
                response: Response<TrainingBackendResponse>
            ) {
                if (response.isSuccessful) {
                    _trainingData.value = response.body()
                    Log.d("TrainingViewModel!!", "Successful response: ${_trainingData.value}")
                } else {
                    Log.e("TrainingViewModel", "Unsuccessful response: ${response.code()}")
                    // Handle unsuccessful response here
                }
            }

            override fun onFailure(call: Call<TrainingBackendResponse>, t: Throwable) {
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

    suspend fun sendResultsToServer(results: List<TrainingResult>) {
        val request = TrainingResultRequest(results)
        val api = TrainingDataService.getInstance()
        val call = api.sendTrainingResults(request)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Retrofit", "Results sent successfully")
                } else {
                    Log.e("Retrofit", "Failed to send results")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
            }
        })
    }
}
