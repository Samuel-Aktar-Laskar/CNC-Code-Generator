package com.example.cnccodegenerator.drawing.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.drawing.Shape

class Line(private val x1: Float,private val y1: Float,private val x2: Float,private val y2: Float) : Shape {


    private val paint  = Paint()

    override fun setStrokeColor(color: Int) {
        paint.color = color
    }

    override fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width.cm()
    }

    override fun setSolidColor(color: Int) {

    }

    override fun draw(canvas: Canvas, originX:Float, originY:Float) {


        canvas.drawLine(
            originX + x1,
            originY - y1,
            originX + x2,
            originY - y2,
            paint
        )
    }

    override fun drawReflection(canvas: Canvas, originX: Float, originY: Float) {
        canvas.drawLine(
            originX + x1,
            originY + y1,
            originX + x2,
            originY + y2,
            paint
        )
    }
}