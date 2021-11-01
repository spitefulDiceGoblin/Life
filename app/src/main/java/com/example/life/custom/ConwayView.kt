package com.example.life.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.life.ConwayArray
import kotlin.math.min

/**
 * TODO: document your custom view class.
 */
class ConwayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // contains array
    private val cells: ConwayArray = ConwayArray()

    // used for drawing
    // created and initialized here instead of when the view is actually drawn,
    // to ensure that the actual drawing step runs as fast as possible

    // Can be implemented better
    private var blockSize: Float = (1).toFloat()
    private var cellSize: Float = (1).toFloat()
    private var lineSize : Float = (1).toFloat()

    // no anti-alias
    //private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        strokeWidth = (0.5).toFloat()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        cellSize = (min(width, height) / 256 ).toFloat()
        lineSize = cellSize /6
        blockSize = cellSize - lineSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.GRAY

        //canvas.drawLine()

        paint.color = Color.BLACK

        // drawing alive cells
        for (cellRow in cells.cells.indices) {
            for (cellColumn in cells.cells[cellRow].indices) {
                val top = cellRow * cellSize
                val left = cellColumn * cellSize

                val bottom = top + blockSize
                val right = left + blockSize

                paint.style = if (cells.cells[cellRow][cellColumn]) {
                    Paint.Style.FILL_AND_STROKE
                } else Paint.Style.STROKE

                canvas.drawRect(left, top, right, bottom, paint)
            }
        }
    }

    init {
        isClickable = true
    }

    /*private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    *//**
     * The text to draw
     *//*
    var exampleString: String?
        get() = _exampleString
        set(value) {
            _exampleString = value
            invalidateTextPaintAndMeasurements()
        }

    *//**
     * The font color
     *//*
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
            invalidateTextPaintAndMeasurements()
        }

    *//**
     * In the example view, this dimension is the font size.
     *//*
    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
            invalidateTextPaintAndMeasurements()
        }

    *//**
     * In the example view, this drawable is drawn above the text.
     *//*
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ConwayView, defStyle, 0
        )

        _exampleString = a.getString(
            R.styleable.ConwayView_exampleString
        )
        _exampleColor = a.getColor(
            R.styleable.ConwayView_exampleColor,
            exampleColor
        )
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _exampleDimension = a.getDimension(
            R.styleable.ConwayView_exampleDimension,
            exampleDimension
        )

        if (a.hasValue(R.styleable.ConwayView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                R.styleable.ConwayView_exampleDrawable
            )
            exampleDrawable?.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = exampleDimension
            it.color = exampleColor
            textWidth = it.measureText(exampleString)
            textHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        exampleString?.let {
            // Draw the text.
            canvas.drawText(
                it,
                paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + (contentHeight + textHeight) / 2,
                textPaint
            )
        }

        // Draw the example drawable on top of the text.
        exampleDrawable?.let {
            it.setBounds(
                paddingLeft, paddingTop,
                paddingLeft + contentWidth, paddingTop + contentHeight
            )
            it.draw(canvas)
        }
    }*/
}