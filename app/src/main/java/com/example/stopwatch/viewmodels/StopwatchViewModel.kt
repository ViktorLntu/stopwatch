package com.example.stopwatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class StopwatchViewModel : ViewModel() {
    private val _isTimerRunning = MutableLiveData<Boolean>()

    val isTimerRunning: LiveData<Boolean>
        get() = _isTimerRunning

    private var timer: Timer? = null
    private var elapsedTimeMillis = 0L

    private val _elapsedTime = MutableLiveData<Long>()

    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    init {
        _isTimerRunning.value = false
    }

    fun startTimer() {
        _isTimerRunning.value = true
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                elapsedTimeMillis += MILLISECONDS_INCREMENT_TIME // Increment by 10 milliseconds
                _elapsedTime.postValue(elapsedTimeMillis)
            }
        }, 0, MILLISECONDS_INCREMENT_TIME)
    }

    fun stopTimer() {
        _isTimerRunning.value = false
        timer?.cancel()
    }

    fun resetTimer() {
        timer?.cancel()
        timer = null
        _isTimerRunning.value = false
        elapsedTimeMillis = 0
        _elapsedTime.postValue(elapsedTimeMillis)
    }

    companion object {
        val MILLISECONDS_INCREMENT_TIME = 10L
    }
}