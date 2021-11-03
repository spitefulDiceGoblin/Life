package com.example.life.custom

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.example.life.ConwayArray
import com.example.life.R
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.math.min

/**
 * TODO: document your custom view class.
 */
class FullConwayView @JvmOverloads constructor(
    // array: Array<BooleanArray>?,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // contains array
    val cells: ConwayArray = ConwayArray()

    // variables for drawing
    // created and initialized here instead of when the view is actually drawn,
    // to ensure that the actual drawing step runs as fast as possible
    // Can be implemented better
    // TODO move hardcoded values to resource file
    private var cellSize: Float = 80f
    private val lineSize get() = cellSize / 16

    // no anti-alias
    //private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    private val paint = Paint()

    init {
        // if (array != null) cells.cells = array

        context.withStyledAttributes(attrs, R.styleable.ConwayView) {
            val stringThing = getString(R.styleable.ConwayView_serializedArray)

            if (stringThing != null && stringThing.length > 10) {
                cells.cells = Json.decodeFromString(stringThing)
            }
        }

        isClickable = true
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        val smallestDimension = min(width, height)
        cellSize = (smallestDimension - 20f) / 256
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

        drawCells(canvas)
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
                    // val top = (cellColumn * cellSize) + (lineSize / 4)
                    // val left = (cellRow * cellSize) + (lineSize / 4)

                    // val bottom = top + blockSize
                    // val right = left + blockSize

                    val top = cellColumn * cellSize
                    val left = cellRow * cellSize

                    val bottom = top + cellSize
                    val right = left + cellSize

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
}