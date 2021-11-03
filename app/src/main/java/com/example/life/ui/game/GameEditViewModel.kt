package com.example.life.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.life.ConwayArray
import kotlinx.coroutines.launch

class GameEditViewModel() : ViewModel() {
    val cellArray = ConwayArray.cells
}