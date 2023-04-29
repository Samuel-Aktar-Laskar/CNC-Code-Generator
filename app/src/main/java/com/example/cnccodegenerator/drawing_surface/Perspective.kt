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
        canvas.scale(scale,scale)
        canvas.translate(delx,dely)
    }
}