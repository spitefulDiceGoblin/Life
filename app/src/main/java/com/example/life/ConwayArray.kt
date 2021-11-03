package com.example.life

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.life.util.Direction

object ConwayArray {
    private var cellArray : Array<BooleanArray> = Array(256) { BooleanArray(256) }
    private var lifeArray : Array<IntArray> = Array(256) { IntArray(256) }

    private val _cells = MutableLiveData<Array<BooleanArray>>()
    val cells: LiveData<Array<BooleanArray>> get() = _cells

    val toRefresh = MutableLiveData<Boolean>()


    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    fun changeState(x: Int, y: Int) {
        cellArray[x][y] = !cellArray[x][y]
        _cells.value = cellArray
    }

    fun clear() {
        cellArray = Array(256) { BooleanArray(256) }
        _cells.value = cellArray
    }


    // TODO: still contains errors
    fun liveLife() {
        val north = shiftArray(cellArray, Direction.NORTH)
        val east = shiftArray(cellArray, Direction.EAST)
        val south = shiftArray(cellArray, Direction.SOUTH)
        val west = shiftArray(cellArray, Direction.WEST)

        val northEast = shiftArray(east, Direction.NORTH)
        val southEast = shiftArray(east, Direction.SOUTH)
        val northWest = shiftArray(west, Direction.NORTH)
        val southWest = shiftArray(west, Direction.SOUTH)

        for (cellRow in cellArray.indices) {
            for (cellColumn in cellArray[cellRow].indices) {
                if (north[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++
                if (east[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++
                if (south[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++
                if (west[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++

                if (northEast[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++
                if (southEast[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++
                if (northWest[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++
                if (southWest[cellRow][cellColumn]) lifeArray[cellRow][cellColumn]++
            }
        }

        for (cellRow in cellArray.indices) {
            for (cellColumn in cellArray[cellRow].indices) {
                cellArray[cellRow][cellColumn] = lifeArray[cellRow][cellColumn] == 3 ||
                        (lifeArray[cellRow][cellColumn] == 4 && cellArray[cellRow][cellColumn])
            }
        }

        _cells.value = cellArray
    }

    private fun shiftArray(array: Array<BooleanArray>, direction: Direction): Array<BooleanArray> {
        var newCells = array.clone()

        when (direction) {
            Direction.NORTH -> {
                val emptyRow = BooleanArray(256)
                newCells = newCells.drop(1).toTypedArray()
                newCells += emptyRow
                return newCells
            }
            Direction.SOUTH -> {
                val emptyRow = BooleanArray(256)
                newCells = newCells.dropLast(1).toTypedArray()
                newCells = Array(1){emptyRow} + newCells
                return newCells
            }
            Direction.EAST -> {
                for (boolArray in newCells) {
                    val index = newCells.indexOf(boolArray)
                    var newLine = boolArray.clone()
                    newLine = newLine.drop(1).toBooleanArray()
                    newLine += false
                    newCells[index] = newLine
                }
                return newCells
            }
            Direction.WEST -> {
                for (boolArray in newCells) {
                    val index = newCells.indexOf(boolArray)
                    var newLine = boolArray.clone()
                    newLine = newLine.dropLast(1).toBooleanArray()
                    newLine = BooleanArray(1) {false} + newLine
                    newCells[index] = newLine
                }
                return newCells
            }
        }
    }

}