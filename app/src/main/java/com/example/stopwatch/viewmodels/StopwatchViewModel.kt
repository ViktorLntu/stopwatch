package com.example.stopwatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class StopwatchViewModel : ViewModel() {
    private var timer: Timer? = null
    private var elapsedTimeMillis = 0L

    private val _elapsedTime = MutableLiveData<Long>()

    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    fun startTimer() {
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                elapsedTimeMillis += MILLISECONDS_INCREMENT_TIME // Increment by 10 milliseconds
                _elapsedTime.value = elapsedTimeMillis
            }
        }, 0, MILLISECONDS_INCREMENT_TIME)
    }

    fun stopTimer() {
        timer?.cancel()
    }

    fun resetTimer() {
        timer?.cancel()
        elapsedTimeMillis = 0
        _elapsedTime.value = 0
    }

    companion object {
        val MILLISECONDS_INCREMENT_TIME = 10L
    }
}