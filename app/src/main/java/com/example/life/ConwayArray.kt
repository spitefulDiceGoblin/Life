package com.example.life

class ConwayArray {
    var cells : Array<BooleanArray> = Array(256) { BooleanArray(256) }

    fun changeState(x: Int, y: Int) {
        cells[x][y] = !cells[x][y]
    }
}