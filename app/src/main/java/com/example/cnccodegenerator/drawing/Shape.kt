package com.example.cnccodegenerator.drawing

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

interface Shape {
    fun setStrokeColor(color: Int)
    /**
     * Enter value in cm*/
    fun setStrokeWidth(width:Float)
    fun setSolidColor(color :Int)
    fun draw(canvas: Canvas, originX: Float, originY : Float)
    fun drawReflection(canvas: Canvas, originX: Float, originY : Float)
}