package com.example.cnccodegenerator.drawing.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.Util
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing_surface.Perspective

class Line(private var x1: Float,private var y1: Float,private var x2: Float,private var y2: Float) : Shape {

    constructor(): this(0f, 0f, 0f, 0f){

    }
    private val paint  = Paint()

    override fun setStrokeColor(color: Int) {
        paint.color = color
    }

    override fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width.cm()
    }

    override fun setSolidColor(color: Int) {

    }

    fun set_start(x1:Float, y1:Float){
        this.x1 = x1
        this.y1 = y1
    }
    fun set_end(x2:Float,y2:Float){
        this.x2 = x2
        this.y2 = y2
    }

    fun draw_normal(canvas: Canvas, originY: Float, perspective: Perspective){
        //Normalize the coordinate
        val start_coor = Util.get_transformed_coordinates(x1,y1,perspective)
        val end_cor = Util.get_transformed_coordinates(x2,y2,perspective)


        canvas.drawLine(start_coor.first, start_coor.second, end_cor.first, end_cor.second,paint)
        canvas.drawLine(start_coor.first,2*originY-start_coor.second,end_cor.first,2*originY-end_cor.second,paint)

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