package com.example.life.custom

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.life.ConwayArray
import kotlin.math.absoluteValue
import kotlin.math.floor


// TODO: better to compose from other vieuws?
// OnScaleGestureListener? SimpleOnScaleGestureListener?
/**
 * TODO: document your custom view class.
 */
class EditableConwayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // contains array
    private val cells: ConwayArray = ConwayArray()

    // variables for dragging
    private var currentDrawingX = 0f
    private var currentDrawingY = 0f

    private var startShiftX = 0f
    private var startShiftY = 0f
    private var endShiftX = 0f
    private var endShiftY = 0f

    private var lastDrawingX = 0f
    private var lastDrawingY = 0f

    // variables for drawing
    // created and initialized here instead of when the view is actually drawn,
    // to ensure that the actual drawing step runs as fast as possible
    // Can be implemented better
    // TODO move hardcoded values to resource file
    private var cellSize: Float = 80f
    private var lineSize : Float = 5f
    private val blockSize get() = cellSize - (3* lineSize)

    // no anti-alias
    //private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    private val paint = Paint()

    init {
        isClickable = true

        currentDrawingX = 128 * blockSize
        currentDrawingY = 128 * blockSize
    }

    // https://stackoverflow.com/questions/19458094/canvas-zooming-in-shifting-and-scaling-on-android
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

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
        for (i in 1..cells.cells.size) {
            val lineXposition = (cellSize * i - lineSize * 1.5f) - currentDrawingY

            // TODO code duplication
            // TODO quickreject for lines
            // TODO bigger line when 5th/10th line
            //if (!isQuickReject(canvas, RectF(0f, lineXposition, width.toFloat(), lineXposition))){
                linePointsArray += 0f
                linePointsArray += lineXposition
                linePointsArray += width.toFloat()
                linePointsArray += lineXposition
            //}

            // canvas.drawLine(0f, lineXposition, width.toFloat(), lineXposition, paint);
        }

        for (i in 1..cells.cells[0].size) {
            val lineYposition = (cellSize * i - lineSize * 1.5f) - currentDrawingX

            // TODO code duplication
            //if (!isQuickReject(canvas, RectF(lineYposition, 0f, lineYposition, height.toFloat()))){
                linePointsArray += lineYposition
                linePointsArray += 0f
                linePointsArray += lineYposition
                linePointsArray += height.toFloat()
            //}

            //canvas.drawLine(lineYposition, 0f, lineYposition, height.toFloat(), paint);
        }

        canvas.drawLines(linePointsArray, paint)
    }

    // Draw alive cells
    private fun drawCells(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.strokeWidth = lineSize
        paint.style = Paint.Style.FILL
        paint.textSize = 100f

        for (cellRow in cells.cells.indices) {
            for (cellColumn in cells.cells[cellRow].indices) {

                // if (cellRow % 10 == 0 && cellColumn % 10 == 0 || cells.cells[cellRow][cellColumn]) {
                if (cells.cells[cellRow][cellColumn]) {
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

        // get the type of action
        // Bit mask of the parts of the action code that are the action itself.
        val action = event.actionMasked
        // val xPosition = event.rawX
        // val yPosition = event.rawY
        val xPosition = event.x
        val yPosition = event.y

        // TODO clickevent
        // Geen ingebouwde click dus tussen deze events plakken
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
                // TODO don't scroll further than edges
                endShiftX = (xPosition - startShiftX) * 2
                endShiftY = (yPosition - startShiftY) * 2

                currentDrawingX = lastDrawingX + endShiftX
                currentDrawingY = lastDrawingY + endShiftY

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
        cells.changeState(cellRow, cellColumn)

        invalidate()

        return true
    }
}