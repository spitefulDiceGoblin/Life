package com.example.life.ui.game

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

class GameRunViewModel() : ViewModel() {
    //val cellArray = ConwayArray.cells

    val refresh = MutableLiveData<Boolean>()

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    init {
        refresh.value = false
    }

    // TODO replace time with value from preferences
    fun startTimer() {
        // executes life
        runnable = Runnable {
            ConwayArray.liveLife()
            refresh.value = true
            // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            handler.postDelayed(runnable, 1000)
        }

        // This is what initially starts the timer
        handler.postDelayed(runnable, 1000)

        // Note that the Thread the handler runs on is determined by a class called Looper.
    }

    fun stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }

    /*
    // TODO check out looper?
    val handler = Handler()

    fun onTick() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                liveLife()
                handler.postDelayed(this, 1000)//1 sec delay
            }
        }, 0)
    }

    fun onTick() {
        viewModelScope.launch {
            ConwayArray.liveLife()
            delay(1_000)
        }

    }

    fun cancelTicks() {
        viewModelScope.cancel()
    }

         */

}