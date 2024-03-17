package com.example.cnccodegenerator

import com.example.cnccodegenerator.drawing_surface.Perspective

object Util {

    fun extractNumbers(input: String): Pair<Float, Float>? {
        val parts = input.split(",")
        if (parts.size != 2) {
            return null
        }
        val num1 = parts[0].toFloatOrNull() ?: return null
        val num2 = parts[1].toFloatOrNull() ?: return null
        return num1 to num2
    }

    fun forn(n:Int, callback:(i:Int)->Unit){
        for(i in 0 until n) callback(i)
    }

    fun get_transformed_coordinates(x:Float, y:Float, perspective: Perspective):Pair<Float,Float>{
        val scale = perspective.scale
        val _x = x/scale
        val _y = y/scale
        val _x_pivot = perspective.scale_pivot_x/scale
        val _y_pivot = perspective.scale_pivot_y/scale
        val X = _x_pivot - perspective.scale_pivot_x + perspective.delx
        val Y = _y_pivot - perspective.scale_pivot_y +  perspective.dely
        val x_transformed = -(X-_x)
        val y_transformed = -(Y-_y)
        return Pair(x_transformed,y_transformed)
    }

}