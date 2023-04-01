package com.example.cnccodegenerator.drawing.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.cnccodegenerator.drawing.Shape

class Line(private val x1: Float,private val y1: Float,private val x2: Float,private val y2: Float) : Shape {

    private fun getPaint():Paint {
        val paint = Paint()
        paint.strokeWidth = 3f
        paint.color = Color.DKGRAY
        return paint
    }

    override fun draw(canvas: Canvas, originX:Float, originY:Float) {


        canvas.drawLine(
            originX + x1,
            originY - y1,
            originX + x2,
            originY - y2,
            getPaint()
        )
    }
}