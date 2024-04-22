package com.example.cnccodegenerator.code_generator

import com.example.cnccodegenerator.code_generator.component_types.PathPoint
import kotlin.math.pow
import kotlin.math.sqrt


fun readjustContourPathAccountingDistanceFromOrigin(contourPath: MutableList<PathPoint>){
    var index = -1
    var distance = Float.MAX_VALUE
    val n = contourPath.size
    for(i in 0 until n){
        val point = contourPath[i]
        val d = sqrt(point.x.pow(2) + point.y.pow(2))
        if (d < distance){
            distance = d
            index = i
        }
    }

    for(i in 0 .. index) {
        contourPath.add(contourPath[i])
    }
    for(i in 0 until  index){
        contourPath.removeFirst()
    }
}

fun generateContourGMCode(contourPath : MutableList<PathPoint>, line : IntWrapper): String {
    readjustContourPathAccountingDistanceFromOrigin(contourPath)
    var gmCode =
        """N${line.incre()} G00 X-20 Y-20
N${line.incre()} G43 Z<tool length compensation> H<enter value> M08 
N${line.incre()} M03 S1000
N${line.incre()} G01 Z<depth value> F50
N${line.incre()} G41 D<tool diameter> F25
    """.trimMargin()
    for(point in contourPath){
        val x :Int= (point.x*10).toInt()
        val y :Int= (point.y*10).toInt()
        gmCode += "\nN${line.incre()} X$x Y$y"
    }
    gmCode += "\nN${line.incre()} G40\n"
    return gmCode
}