package com.example.life.ui.game.run

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.life.ConwayArray

class GameRunViewModel : ViewModel() {

    private var ticks = 0
    private lateinit var handler: Handler

    private var task =  object : Runnable {
        override fun run() {
            refresh.value = true
            ticks++
            ticksAsString.value = ticks.toString()
            handler.postDelayed(this, 1000)
        }
    }

    val refresh = MutableLiveData<Boolean>()
    val aliveCellsAsString = MutableLiveData<String>()
    val ticksAsString = MutableLiveData<String>()

    init {
        refresh.value = false
        handler = Handler(Looper.getMainLooper())
    }

    fun updateAlive() {
        aliveCellsAsString.value = ConwayArray.aliveCells.value.toString()
    }

    // TODO replace time with value from preferences
    fun startTimer() {
        handler.post(task)
    }

    fun stopTimer() {
        handler.removeCallbacks(task)
    }
}