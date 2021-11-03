package com.example.life.custom

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.example.life.ConwayArray
import kotlin.math.min


// TODO move hardcoded values to resource file
// TODO move to data class
class FullConwayView @JvmOverloads constructor(
    // array: Array<BooleanArray>?,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var cells: Array<BooleanArray>

    // variables for drawing
    private var cellSize: Float = 80f
    private val lineSize get() = cellSize / 16
    private val paint = Paint()

    init {
        isClickable = true
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        val smallestDimension = min(width, height)
        cellSize = (smallestDimension - 20f) / 256
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        cells = ConwayArray.cells.value!!

        // draw edge
        canvas.drawColor(Color.MAGENTA)

        //clip
        canvas.clipRect(
            10f, 10f, width - 10f, height - 10f
        )

        // draw bg
        canvas.drawColor(Color.WHITE)

        drawCells(canvas)
    }

    fun liveLife() {
        ConwayArray.liveLife()
        invalidate()
    }

    fun clear() {
        ConwayArray.liveLife()
        invalidate()
    }

    // Draw alive cells
    private fun drawCells(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.strokeWidth = lineSize
        paint.style = Paint.Style.FILL
        paint.textSize = 100f

        for (cellRow in cells.indices) {
            for (cellColumn in cells[cellRow].indices) {
                if (cells[cellRow][cellColumn]) {
                    val top = cellColumn * cellSize
                    val left = cellRow * cellSize

                    val bottom = top + cellSize
                    val right = left + cellSize

                    val cell = RectF(left, top, right, bottom)

                    if (!isQuickReject(canvas, cell)) canvas.drawRect(cell, paint)
                }
            }
        }
    }

    // Check whether rectangle is outside of canvas area
    private fun isQuickReject (canvas: Canvas, cell: RectF) : Boolean {
        if (Build.VERSION.SDK_INT < 30) return canvas.quickReject(cell, Canvas.EdgeType.BW)
        return canvas.quickReject(cell)
    }
}