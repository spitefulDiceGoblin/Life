package com.example.life.ui.game

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.life.ConwayArray
import com.example.life.util.Direction
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class GameRunViewModel() : ViewModel() {
    val refresh = MutableLiveData<Boolean>()

    lateinit var handler: Handler
    var task =  object : Runnable {
        override fun run() {
            refresh.value = true
            handler.postDelayed(this, 1000)
        }
    }

    init {
        refresh.value = false
        handler = Handler(Looper.getMainLooper())
    }

    // TODO replace time with value from preferences
    fun startTimer() {
        handler.post(task)
    }

    fun stopTimer() {
        handler.removeCallbacks(task)
    }
}