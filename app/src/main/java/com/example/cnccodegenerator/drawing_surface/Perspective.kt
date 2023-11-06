package com.example.cnccodegenerator.drawing_surface

import android.graphics.Canvas
import android.graphics.Matrix
import kotlin.math.max
import kotlin.math.min


const val MIN_SCALE = 0.1f
const val MAX_SCALE = 100f
class Perspective {

    var surfaceScale = 1f
    var actualScale = 1f
    var delx=200f
    var dely=200f
    var scale_pivot_x = 0f
    var scale_pivot_y = 0f

    var scale: Float
        get() = surfaceScale
        set(scale) {
            surfaceScale = max(MIN_SCALE, min(MAX_SCALE, scale))
        }


    fun multiplyScale(factor: Float) {
        scale = surfaceScale * factor
        actualScale *= factor
    }

    fun translate(dx:Float, dy:Float){
        delx += dx
        dely += dy
    }


    fun applyToCanvas(canvas: Canvas) {
        scale_pivot_x = canvas.width/2f
        scale_pivot_y = canvas.height/2f
        canvas.scale(scale,scale,scale_pivot_x, scale_pivot_y)
        canvas.translate(delx,dely)
    }


}