package com.example.wb_week_6_1.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wb_week_6_1.utills.calculatePi

class PiViewModel : ViewModel() {
    val liveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private lateinit var runnableCalculatePi: Runnable
    private lateinit var runnableColor: Runnable
    private var handlerUI: Handler = Handler(Looper.getMainLooper())
    private var resultPi = ""
    var countLengthString = 1
    private var isRunningCalculate = false
    val maxLengthString = 2147483647
    var color = 0


    fun startCalculatePi() {
        isRunningCalculate = true
        getCalculatePi()
    }

    private fun getCalculatePi() {
        runnableCalculatePi = Runnable {
            while (isRunningCalculate) {
                isRunningCalculate = countLengthString < maxLengthString
                countLengthString += 1
                resultPi = calculatePi(countLengthString)
                handlerUI.post() {
                    liveData.postValue(resultPi)
                }
            }
        }
        Thread(runnableCalculatePi).start()

    }

    fun pauseCalculatePi() {
        isRunningCalculate = false
        handlerUI.removeCallbacks(runnableCalculatePi)
    }

    fun resetCalculatePi() {
        countLengthString = 0
        resultPi = ""
        if (!isRunningCalculate) {
            startCalculatePi()
        } else {
            handlerUI.removeCallbacks(runnableCalculatePi)
        }


    }

}