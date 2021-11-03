package com.example.life.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameEditViewModel() : ViewModel() {
    private var cells : Array<BooleanArray> = Array(256) { BooleanArray(256) }


    private val _cellArray = MutableLiveData<Array<BooleanArray>>()
    val cellArray: LiveData<Array<BooleanArray>> get() = _cellArray
}