package com.example.cnccodegenerator.drawing.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.Util
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing_surface.Perspective
import java.text.Format

data class Drill(  var x: Float, var y: Float,
              val paint: Paint = Paint().apply {
                  color = Color.DKGRAY
                  style = Paint.Style.STROKE
                  strokeWidth = 0.05.cm()
              }) : Shape{


                  private lateinit var perspective : Perspective

    private var originX = 0f
    private var originY = 0f

    fun setOrigin(x:Float,y:Float){
        originX = x
        originY = y
    }


                  fun setPerspective(perspective: Perspective){
                      this.perspective = perspective
                  }
    override fun setStrokeColor(color: Int) {
        paint.color = color
    }

    override fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width
    }

    override fun setSolidColor(color: Int) {
    }
    fun setCenter(x:Float, y:Float){
        val coor = Util.get_transformed_coordinates(x,y,perspective)
//        val transformedPoints = PointF(coor.first-originX, originY - coor.second)
        this.x = coor.first-originX
        this.y = originY - coor.second
    }

    override fun draw(canvas: Canvas, originX: Float, originY: Float) {
        val aX = originX+x
        val aY = originY - y
        canvas.drawCircle(originX+x, originY-y,0.2.cm(),paint)
        canvas.drawLine(aX, aY+0.4.cm(), aX, aY-0.4.cm(), paint)
        canvas.drawLine(aX+0.4.cm(), aY, aX-0.4.cm(), aY, paint)
    }

    override fun drawReflection(canvas: Canvas, originX: Float, originY: Float) {
        TODO("Not yet implemented")
    }

}
