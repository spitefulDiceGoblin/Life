package com.example.life

class ConwayArray {
    var cells = arrayOf<Array<Boolean>>()

    constructor() {
        // TODO: initialize 2D array with sizes?
        for (i in 0..255) {
            var array = arrayOf<Boolean>()
            for (j in 0..255) {
                array += false
            }
            cells += array
        }
    }

    constructor(array: Array<Array<Boolean>>) {
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