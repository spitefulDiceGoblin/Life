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

    private val timer: Timer

    init {
        refresh.value = false

        timer = Timer(true)


        //timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
        //
        //    override fun onTick(millisUntilFinished: Long) {
        //        _currentTime.value = millisUntilFinished/ONE_SECOND
        //    }
        //
        //    override fun onFinish() {
        //        _currentTime.value = DONE
        //        onGameFinish()
        //    }
        //}
    }

    // TODO replace time with value from preferences
    fun startTimer() {
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    test()
                }

            },0, 1000
        )
    }

    fun stopTimer() {
        timer.cancel()
        timer.purge()
    }

    private fun test() {
        ConwayArray.toRefresh.value = true
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