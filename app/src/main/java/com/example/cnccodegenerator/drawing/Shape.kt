package com.example.cnccodegenerator.drawing

import android.graphics.Canvas

interface Shape {
    fun draw(canvas: Canvas, originX: Float, originY : Float)
}