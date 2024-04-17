package com.example.cnccodegenerator.code_generator

import com.example.cnccodegenerator.code_generator.component_types.PathPoint
import kotlin.math.max

fun outer(a : MutableList<Float>, b : MutableList<Float>): Boolean{
    return a[0] < b[0] && a[1] < b[1] && a[2] > b[2] && a[3] > b[3];
}

fun findContour(outlines : MutableList<MutableList<PathPoint>>): Int {
    val n = outlines.size
    val extremes = MutableList<MutableList<Float>>(n){ mutableListOf(Float.MAX_VALUE,Float.MAX_VALUE,Float.MIN_VALUE, Float.MIN_VALUE)}
    for(i in 0 until n){
        for(point in outlines[i]){
            val x = point.x
            val y = point.y
            extremes[i][0] = min(extremes[i][0], x)
            extremes[i][1] = min(extremes[i][1], y)
            extremes[i][2] = max(extremes[i][2], x)
            extremes[i][3] = max(extremes[i][3], y)
        }
    }

    var extremeIndex = 1
    for(i in 0 until n){
        var isOuter = true;
        for(j in 0 until n){
            if (i == j) continue
            if (!outer(extremes[i], extremes[j])){
                isOuter= false
                break;
            }
        }
        if (isOuter){
            extremeIndex = i;
            break;
        }
    }
    return extremeIndex
}