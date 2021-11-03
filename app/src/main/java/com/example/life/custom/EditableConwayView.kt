package com.example.life.custom

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.preference.PreferenceManager
import com.example.life.ConwayArray
import com.example.life.R
import java.lang.Float.max
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.min

class EditableConwayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var isScrollingCounter = false

    // contains array
    private lateinit var cells: Array<BooleanArray>

    // variables for dragging
    private var currentDrawingX = 0f
    private var currentDrawingY = 0f
    private var lastDrawingX = 0f
    private var lastDrawingY = 0f

    private var startShiftX = 0f
    private var startShiftY = 0f
    private var endShiftX = 0f
    private var endShiftY = 0f

    // variables for drawing
    private var cellSize: Float = 80f
    private val lineSize get() = cellSize / 16
    private val blockSize get() = cellSize - (3* lineSize)
    private val paint = Paint()



    init {
        context.withStyledAttributes(attrs, R.styleable.ConwayView) {
            isScrollingCounter = getBoolean(R.styleable.ConwayView_isCounterScroll, true)
        }

        isClickable = true

        currentDrawingX = 128 * blockSize
        currentDrawingY = 128 * blockSize
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

        drawGrid(canvas)
        drawCells(canvas)
    }

    // Draw gridlines
    private fun drawGrid(canvas: Canvas) {
        paint.color = Color.GRAY
        paint.strokeWidth = lineSize

        var linePointsArray = floatArrayOf()

        // Not assuming that we have a square
        for (i in 1 until cells.size) {
            val lineXposition = (cellSize * i - lineSize * 1.5f) - currentDrawingY

            linePointsArray += 0f
            linePointsArray += lineXposition
            linePointsArray += width.toFloat()
            linePointsArray += lineXposition
        }

        for (i in 1 until cells[0].size) {
            val lineYposition = (cellSize * i - lineSize * 1.5f) - currentDrawingX

            linePointsArray += lineYposition
            linePointsArray += 0f
            linePointsArray += lineYposition
            linePointsArray += height.toFloat()
        }

        canvas.drawLines(linePointsArray, paint)
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
                    val top = (cellColumn * cellSize) + (lineSize / 4) - currentDrawingY
                    val left = (cellRow * cellSize) + (lineSize / 4) - currentDrawingX

                    val bottom = top + blockSize
                    val right = left + blockSize

                    val cell = RectF(left, top, right, bottom)

                    if (!isQuickReject(canvas, cell)) canvas.drawRect(cell, paint)
                    //if (!isQuickReject(canvas, cell)) canvas.drawText("${cellRow.toString()}/${cellColumn.toString()}", left, top, paint)
                }
            }
        }
    }

    // Check whether rectangle is outside of canvas area
    private fun isQuickReject (canvas: Canvas, cell: RectF) : Boolean {
        if (Build.VERSION.SDK_INT < 30) return canvas.quickReject(cell, Canvas.EdgeType.BW)
        return canvas.quickReject(cell)
    }

    // Source: https://android-developers.googleblog.com/2010/06/making-sense-of-multitouch.html
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val xPosition = event.x
        val yPosition = event.y

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                lastDrawingX = currentDrawingX
                lastDrawingY = currentDrawingY

                startShiftX = xPosition
                startShiftY = yPosition

                endShiftX = xPosition
                endShiftY = yPosition
            }
            MotionEvent.ACTION_MOVE -> {
                endShiftX = (startShiftX - xPosition) * 2
                endShiftY = (startShiftY - yPosition) * 2

                if (isScrollingCounter) {
                    currentDrawingX = lastDrawingX + endShiftX
                    currentDrawingY = lastDrawingY + endShiftY
                } else {
                    currentDrawingX = lastDrawingX - endShiftX
                    currentDrawingY = lastDrawingY - endShiftY
                }

                currentDrawingX = max(currentDrawingX, 0f)
                currentDrawingY = max(currentDrawingY, 0f)

                currentDrawingX = min(currentDrawingX, (cellSize * 256 - width))
                currentDrawingY = min(currentDrawingY, (cellSize * 256 - height))

                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                val xShift = startShiftX - endShiftX
                val yShift = startShiftY - endShiftY

                if (xShift.absoluteValue < 5f && yShift.absoluteValue < 5f) {
                    return performClick()
                }
            }
        }

        return true
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true

        // Get X and Y coordinates for cell
        val xForClick = currentDrawingX + startShiftX
        val yForClick = currentDrawingY + startShiftY

        // Translate to X/Y in Array

        val cellRow = floor(xForClick / cellSize).toInt()
        val cellColumn = floor(yForClick / cellSize).toInt()

        // Change cell value
        ConwayArray.changeState(cellRow, cellColumn)

        invalidate()

        return true
    }

    fun clear() {
        ConwayArray.clear()
        invalidate()
    }
}