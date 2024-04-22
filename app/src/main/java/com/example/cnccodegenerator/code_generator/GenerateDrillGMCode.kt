package com.example.cnccodegenerator.code_generator

import com.example.cnccodegenerator.Constants
import com.example.cnccodegenerator.Dimensions
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing.shapes.Drill

fun generateDrillGmCode(components: List<Shape>, line : IntWrapper): String {
    var gmCode = "N${line.incre()} G81 Z-10 R10 "
    for(component in components){
        if (component !is Drill) continue
        val x :Int= (component.x*10/Dimensions.CENTIMETER).toInt()
        val y :Int= (component.y*10/Dimensions.CENTIMETER).toInt()
        gmCode += "\nN${line.incre()} X$x Y$y"
    }
    gmCode += "\nN${line.incre()} G80\n"
    return gmCode
}
