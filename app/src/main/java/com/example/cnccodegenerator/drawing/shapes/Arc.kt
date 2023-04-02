package com.example.cnccodegenerator.drawing.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.Log
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.drawing.Shape
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

private const val TAG = "Arc"
class Arc constructor(
    start :Point,
    second: Point,
    end: Point

) : Shape {
    private fun calculateCenter(start: Point, second: Point, end: Point): Triple<Float, Float, Float> {
        val _ma = (second.y - start.y) / (second.x - start.x).toFloat()
        val _mb = (end.y - second.y) / (end.x - second.x).toFloat()
        val xa = (start.x + second.x)/2f
        val ya = (start.y + second.y)/2f
        val xb = (second.x + end.x)/2f
        val yb = (second.y + end.y)/2f

        val ma = -1f/_ma;
        val mb = -1f/_mb




        val centerX = (yb - ya + ma*xa - mb * xb)/(ma - mb)
        val centerY = ya + ma*centerX- ma*xa
        val radius = sqrt((centerX - start.x).pow(2) + (centerY - start.y).pow(2))
        return Triple(centerX, centerY, radius)
    }

    private fun calculateAngle(centerX: Float, centerY: Float, point: Point): Float {
        val dx = point.x - centerX
        val dy = point.y - centerY
        val angle = Math.toDegrees(kotlin.math.atan2(abs(dy), abs(dx)).toDouble()).toFloat()
        return if (dx > 0 && dy > 0) angle
        else if (dx < 0 && dy > 0) 180f- angle;
        else if (dx < 0 && dy<0) 180f + angle
        else  360f-angle;
    }



    private val startAngle: Float
    private val endAngle: Float
    private var centerX: Float
    private var centerY: Float
    private val radius: Float
    private var sweepAngle : Float

    init {
        // Calculate the center point and radius of the circle that the arc will be a part of
        val (centerXResult, centerYResult, radiusResult) = calculateCenter(start, second, end)
        centerX = centerXResult
        centerY = centerYResult
        radius = radiusResult

        // Calculate the start angle and end angle of the arc
        startAngle = calculateAngle(centerX, centerY, start)
        endAngle = calculateAngle(centerX, centerY, end)
        val secondAngle = calculateAngle(centerX, centerY, second)

        // Calculate the sweep angle of the arc
        sweepAngle = abs(endAngle - startAngle)

        if (secondAngle > startAngle && endAngle > secondAngle){
            //anti clockwise dir from A
            sweepAngle = -sweepAngle
        }
        else if (secondAngle < startAngle && endAngle < secondAngle) {
            //clock wise dir
        }
        else if (endAngle > startAngle) {
            sweepAngle = 360 - sweepAngle
        }
        else {
            sweepAngle = sweepAngle - 360
        }


    }




    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 0.05.cm()
    }

    override fun setStrokeColor(color: Int) {
        paint.color = color
    }

    override fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width.cm()
    }

    override fun setSolidColor(color: Int) {

    }

    override fun draw(canvas: Canvas, originX: Float, originY: Float) {
        centerX += originX
        centerY = originY - centerY
        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
            360f-startAngle, sweepAngle, false, paint)
        Log.d(TAG, "draw: startAngle ${360-startAngle} and sweepAngle $sweepAngle")
    }

    override fun drawReflection(canvas: Canvas, originX: Float, originY: Float) {

    }
}