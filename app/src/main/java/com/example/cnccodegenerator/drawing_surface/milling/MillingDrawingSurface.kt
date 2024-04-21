package com.example.cnccodegenerator.drawing_surface.milling

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.drawing.shapes.Drill
import com.example.cnccodegenerator.drawing_surface.DrawingSurface


private const val TAG = "DrawingSurface"
open class MillingDrawingSurface : DrawingSurface {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrSet: AttributeSet?) : super(context, attrSet)

    fun toggleDrillShow(){
        drillShow = true
        refreshDrawingSurface()
    }

    override fun drawGrid(canvas: Canvas?) {
        super.drawGrid(canvas)
        val width = canvas!!.width
        val height = canvas.height
        val originY = height / 2f
        val originX = width / 7f
        canvas.drawLine((-300).cm(), originY, 300.cm(), originY, Paint().apply { color= Color.GRAY
            strokeWidth = 4f/perspective.scale
        })

        if (drillShow){
            drillTouch.setOrigin(originX,originY)
        }
        //drawing components
        components.forEach {
            it.draw(canvas,originX, originY)
        }

        if (draw_line){
            line_path.draw_normal(canvas,drawReflection = false)
        }

        if (drillShow){
            drillTouch.draw(canvas,originX,originY)
        }
    }
}