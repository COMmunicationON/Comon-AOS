package com.example.comon.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comon.model.TrainingBackendResponse
import com.example.comon.model.TrainingModel
import com.example.comon.network.TrainingDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class TrainingViewModel : ViewModel() {
    //전체, 단어, 음절, 음소

    private val _trainingData = MutableLiveData<TrainingBackendResponse>()
    val trainingData: LiveData<TrainingBackendResponse> = _trainingData

    fun fetchTrainingData(trainingModel: TrainingModel) {
        val api = TrainingDataService.getInstance()

        api.postTrainingData(trainingModel).enqueue(object : Callback<TrainingBackendResponse> {
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
}
