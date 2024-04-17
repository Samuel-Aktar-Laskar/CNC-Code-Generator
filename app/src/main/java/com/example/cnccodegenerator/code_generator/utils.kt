package com.example.cnccodegenerator.code_generator

import android.graphics.PointF
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


const val epsilon= 0.0001f

fun PointF.distance(end : PointF) : Float {
    return sqrt(  abs(this.x - end.x).pow(2) + abs(this.y-end.y).pow(2))
}


fun PointF.overlap(end: PointF): Boolean {
    return this.distance(end) < epsilon
}

class IntWrapper(value: Int) {
    var value : Int = 0
    fun incre(amount : Int = 1): Int {
        return value++
    }
}

