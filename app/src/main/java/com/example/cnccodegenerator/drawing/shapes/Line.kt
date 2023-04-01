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

    override fun draw(canvas: Canvas) {
        canvas.drawLine(
            x1,y1,
            x2,y2,
            getPaint()
        )
    }
}