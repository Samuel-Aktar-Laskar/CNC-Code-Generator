package com.example.cnccodegenerator.drawing_surface.turning

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.drawing_surface.DrawingSurface


private const val TAG = "DrawingSurface"
open class TurningDrawingSurface : DrawingSurface {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrSet: AttributeSet?) : super(context, attrSet)

    override fun drawGrid(canvas: Canvas?) {
        super.drawGrid(canvas)
        val width = canvas!!.width
        val height = canvas.height
        val originY = height / 2f
        val originX = width / 7f

        //drawing center line
        val paint = Paint()
        paint.color = Color.GRAY
        paint.strokeWidth=4f/perspective.scale
        paint.pathEffect = DashPathEffect(floatArrayOf(80f, 10f, 20f, 10f), 0f)

        canvas.drawLine((-300).cm(), originY,300.cm(),originY, paint )

        //drawing components
        components.forEach {
            it.draw(canvas,originX, originY)
            it.drawReflection(canvas,originX,originY)
        }


        if (draw_line){
            line_path.draw_normal(canvas)
        }

    }
}