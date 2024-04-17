package com.example.cnccodegenerator.code_generator

import com.example.cnccodegenerator.code_generator.component_types.Circle
import com.example.cnccodegenerator.code_generator.component_types.Groove
import com.example.cnccodegenerator.code_generator.component_types.PathPoint

class PathData(
    var grooves: MutableList<Groove> = mutableListOf(),
    var outlines: MutableList<MutableList<PathPoint>> = mutableListOf(),
    var drills : MutableList<Circle> = mutableListOf(),
    var contour : MutableList<PathPoint> = mutableListOf()
) {
}
