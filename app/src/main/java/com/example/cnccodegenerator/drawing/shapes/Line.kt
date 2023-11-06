package com.example.cnccodegenerator.drawing.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.Util
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing_surface.Perspective

data class Line( private var x1: Float,private var y1: Float, private var x2: Float,private var y2: Float, private val paint: Paint=Paint()) : Shape {

    private lateinit var perspective: Perspective
    private var originX = 0f
    private var originY = 0f

    fun setOrigin(x:Float,y:Float){
        originX = x
        originY = y
    }

    fun setPerspective(perspective: Perspective){
        this.perspective = perspective
    }
    constructor(): this(0f, 0f, 0f, 0f, Paint()){

    }


    override fun setStrokeColor(color: Int) {
        paint.color = color
    }

    override fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width.cm()
    }

    override fun setSolidColor(color: Int) {

    }

    fun set_start(x1:Float, y1:Float){
        val coor = Util.get_transformed_coordinates(x1,y1,perspective)
        this.x1 = coor.first-originX
        this.y1 = originY - coor.second
    }
    fun set_end(x2:Float,y2:Float){
        val coor = Util.get_transformed_coordinates(x2,y2,perspective)
        this.x2 = coor.first-originX
        this.y2 = originY - coor.second
    }



    fun draw_normal(canvas: Canvas){
        draw(canvas, originX, originY)
        drawReflection(canvas, originX, originY)
    }

    fun reset_line(){
        x1=0f
        x2=0f
        y1=0f
        y2=0f
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