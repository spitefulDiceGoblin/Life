package com.example.life

// TODO Move logic to viewmodels
class ConwayArray {
    var cells = arrayOf<BooleanArray>()

    constructor() {
        // TODO: initialize 2D array with sizes?
        for (i in 0..255) {
            cells += BooleanArray(256)
        }
    }

    constructor(array: Array<BooleanArray>) {
        cells = array
    }

    // TODO: get value for x - y cell

    fun changeState(x: Int, y: Int) {
        cells[x][y] = !cells[x][y]
    }

    fun setToAlive(x: Int, y: Int) {
        cells[x][y] = true
    }

    fun setToDead(x: Int, y: Int) {
        cells[x][y] = false
    }
}