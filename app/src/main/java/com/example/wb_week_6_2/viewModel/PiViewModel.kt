package com.example.wb_week_6_2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wb_week_6_2.utills.calculatePi
import kotlinx.coroutines.*

class PiViewModel : ViewModel() {
    val liveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private var resultPi = ""
    private var countLengthString = 1
    private var isRunningCalculate = false
    private val maxLengthString = 2147483647
     val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Default
    )


    fun startCalculatePi() {
        isRunningCalculate = true
        viewModelCoroutineScope.launch {
            getCalculatePi()
        }
    }

    private suspend fun getCalculatePi() {
        withContext(Dispatchers.Default) {
            while (isRunningCalculate) {
                isRunningCalculate = countLengthString < maxLengthString
                countLengthString += 1
                resultPi = calculatePi(countLengthString)
                Dispatchers.Main {
                    liveData.postValue(resultPi)
                }
            }
        }
    }

    fun pauseCalculatePi() {
        isRunningCalculate = false
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    fun resetCalculatePi() {
        countLengthString = 0
        resultPi = ""
        if (!isRunningCalculate) {
            startCalculatePi()
        }

    }

}