package com.example.life.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.life.util.Direction
import kotlinx.coroutines.launch

class GameRunViewModel() : ViewModel() {
    private var cells : Array<BooleanArray> = Array(256) { BooleanArray(256) }
    private var life : Array<IntArray> = Array(256) { IntArray(256) }

    fun onTick() {
        viewModelScope.launch {
            liveLife()
        }
    }

    private val _cellArray = MutableLiveData<Array<BooleanArray>>()
    val cellArray: LiveData<Array<BooleanArray>> get() = _cellArray

    fun liveLife() {
        val north = shiftArray(cells, Direction.NORTH)
        val east = shiftArray(cells, Direction.EAST)
        val south = shiftArray(cells, Direction.SOUTH)
        val west = shiftArray(cells, Direction.WEST)

        val northEast = shiftArray(east, Direction.NORTH)
        val southEast = shiftArray(east, Direction.SOUTH)
        val northWest = shiftArray(west, Direction.NORTH)
        val southWest = shiftArray(west, Direction.SOUTH)

        for (cellRow in cells.indices) {
            for (cellColumn in cells[cellRow].indices) {
                if (north[cellRow][cellColumn]) life[cellRow][cellColumn]++
                if (east[cellRow][cellColumn]) life[cellRow][cellColumn]++
                if (south[cellRow][cellColumn]) life[cellRow][cellColumn]++
                if (west[cellRow][cellColumn]) life[cellRow][cellColumn]++

                if (northEast[cellRow][cellColumn]) life[cellRow][cellColumn]++
                if (southEast[cellRow][cellColumn]) life[cellRow][cellColumn]++
                if (northWest[cellRow][cellColumn]) life[cellRow][cellColumn]++
                if (southWest[cellRow][cellColumn]) life[cellRow][cellColumn]++
            }
        }

        for (cellRow in cells.indices) {
            for (cellColumn in cells[cellRow].indices) {
                cells[cellRow][cellColumn] = life[cellRow][cellColumn] == 3 ||
                        (life[cellRow][cellColumn] == 4 && cells[cellRow][cellColumn])
            }
        }

        _cellArray.value = cells
    }

    private fun shiftArray(array: Array<BooleanArray>, direction: Direction): Array<BooleanArray> {
        when (direction) {
            Direction.NORTH -> {
                val newCells = array.clone()
                val emptyRow = BooleanArray(256)
                newCells.drop(1)
                return newCells + emptyRow
            }
            Direction.SOUTH -> {
                val newCells = array.clone()
                val emptyRow = BooleanArray(256)
                newCells.dropLast(1)
                return Array(1){emptyRow} + newCells
            }
            Direction.EAST -> {
                var newCells = arrayOf<BooleanArray>()
                for (boolArray in newCells) {
                    var newLine = boolArray.clone()
                    newLine.drop(1)
                    newLine += false
                    newCells += newLine
                }
                return newCells
            }
            Direction.WEST -> {
                var newCells = arrayOf<BooleanArray>()
                for (boolArray in newCells) {
                    var newLine = boolArray.clone()
                    newLine.dropLast(1)
                    newLine += false
                    newCells += newCells
                }
                return newCells
            }
        }
    }

}