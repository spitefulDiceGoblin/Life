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
}