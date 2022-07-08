package com.example.moviesearch.view.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.moviesearch.R
import kotlin.properties.Delegates

class RatingDonutView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var radius: Float = 0f
    private var stroke: Float = 10f
    private val scaleSize: Float = 60f
    var rating: Double = 0.0

    private lateinit var strokePaint: Paint
    private lateinit var digitPaint: Paint
    private lateinit var circlePaint: Paint

    private val oval = RectF()

    init {
        val a =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.RatingDonutView, 0, 0)
        try {
            stroke = a.getFloat(R.styleable.RatingDonutView_stroke, stroke)
            rating = a.getInt(R.styleable.RatingDonutView_rating, rating.toInt()).toDouble()
        } finally {
            a.recycle()
        }
        initPaint()
    }

    //метод для инициализации объектов Paint()
    private fun initPaint() {
        //краска для колец
        strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = stroke
            color = getPaintColor(rating)
            isAntiAlias = true
        }
        //краска для рейтинга
        digitPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 2f
            setShadowLayer(5f, 0f, 0f, Color.GRAY)
            textSize = scaleSize
            typeface = Typeface.SANS_SERIF
            color = getPaintColor(rating)
            isAntiAlias = true
        }
        //краска для заднего фона
        circlePaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            color = Color.DKGRAY
        }
    }

    //метод для выбора соответствующего рейтингу цвета
    private fun getPaintColor(rating: Double): Int = when (rating) {
        in 0.0..2.5 -> Color.parseColor("#F82313")
        in 2.6..5.0 -> Color.parseColor("#FF5722")
        in 5.1..7.5 -> Color.parseColor("#9abb01")
        else -> Color.parseColor("#3AB35D")
    }

    //метод для определения радиуса
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = if (width > height) {
            height.div(2f)
        } else {
            width.div(2f)
        }
    }

    //метод для определения размера View
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)

        val minSize = chosenHeight.coerceAtMost(chosenWidth)

        centerX = minSize.div(2f)
        centerY = minSize.div(2f)

        setMeasuredDimension(minSize, minSize)
    }

    private fun chooseDimension(mode: Int, size: Int) = when (mode) {
        MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
        else -> 300
    }

    //метод для отрисовки арки рейтинга
    private fun drawRating(canvas: Canvas) {
        val scale = radius * 0.85f
        canvas.save()
        canvas.translate(centerX, centerY)
        oval.set(0f - scale, 0f - scale, scale, scale)
        canvas.drawCircle(0f, 0f, radius, circlePaint)
        canvas.drawArc(oval, -90f, convertProgressToDegrees(rating), false, strokePaint)
        canvas.restore()
    }

    //метод для конвертации рейтинга в градусы для отрисовки арки
    private fun convertProgressToDegrees(rating: Double): Float = rating.toFloat() * 36f

    //метод для отрисовки текста - значения рейтинга
    private fun drawText(canvas: Canvas) {
        val text = String.format("%.1f", rating * 1f)
        val widths = FloatArray(text.length)
        digitPaint.getTextWidths(text, widths)
        var advance = 0f
        for (width in widths) advance += width
        canvas.drawText(text, centerX - advance / 2, centerY + advance / 5, digitPaint)
    }

    override fun onDraw(canvas: Canvas) {
        drawRating(canvas)
        drawText(canvas)
    }
}