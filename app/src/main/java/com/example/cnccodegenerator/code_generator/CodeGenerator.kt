package com.example.cnccodegenerator.code_generator

import android.util.Log
import com.example.cnccodegenerator.code_generator.component_types.PathPoint
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing.shapes.Line

private const val TAG = "CodeGenerator"
class CodeGenerator {

    var line = IntWrapper(1)

    fun initialSetup() : String{
        val setup = """
            %
            N${line.incre()} O10001 (Your program title)
            N${line.incre()} G21 
            N${line.incre()} G17 G40 G80 G49 G90
            N${line.incre()} G92 X-<x origin value> Y-<y origin value> ;replace them with actual values
            N${line.incre()} M06 T01
            
        """.trimIndent()
        return setup
    }
    fun transformToPathData(
        components: List<Shape>
    ): PathData {
        val n = components.size
        val nodesDone = MutableList(n){false}
        val closedLoopComponents: MutableList<List<Node>> = extractClosedLoops(components,nodesDone)

        val outlines = mutableListOf<MutableList<PathPoint>>()

        for(loop in closedLoopComponents){
            val outline = mutableListOf<PathPoint>()
            val n = loop.size
            if (n == 0) continue;
            val firstNode = loop[0]
            if (n == 1){
                nodesDone[firstNode.index] = false;
                continue;
            }
            val secondNode = loop[1];
            var start = firstNode.startPoint
            var end = firstNode.endPoint
            if (firstNode.startPoint.overlap(secondNode.startPoint) || firstNode.startPoint.overlap(secondNode.endPoint)){
                start = firstNode.endPoint
                end = firstNode.startPoint
            }
            
            outline.add(PathPoint(end.x, end.y))

            for(i in 1 until n){
               val node = loop[i]
               if (end.overlap(node.startPoint)){
                   end = node.endPoint
               }
                else if (end.overlap(node.endPoint)){
                    end = node.startPoint
               }
                else {
                   Log.e(TAG, "generateGMCode: Error no overlap between index ${i-1} & ${i} found", )
               }
                outline.add(PathPoint(end.x, end.y))

            }
            var pt= "";
            for(x in outline){
                pt += "(${x.x},${x.y}), "
            }
            Log.d(TAG, "generateGMCode: outline : $pt")
            outlines.add(outline)
        }
        val contourIndex = findContour(outlines)
        val contourOutline = outlines[contourIndex]
        outlines.removeAt(contourIndex)
        val pathData = PathData()
        pathData.outlines = outlines
        pathData.contour = contourOutline
        return pathData
    }

    fun endPartCode(): String {
        return """
           N${line.incre()} G00 Z10
           N${line.incre()} M05 M09
           N${line.incre()} G28 X0 Y0
           N${line.incre()} M30
           %
        """.trimIndent()
    }

    fun printComponents(components: List<Shape>){
        var out = "Printing the components \n"
        for(shape in components){
            if (shape is Line){
                out += "(${shape.x1},${shape.y1}) to (${shape.x2},${shape.y2}) \n"
            }
        }
        Log.d(TAG, "printComponents: $out")
    }

    fun generateGMCode(components : List<Shape>): String {
        printComponents(components)
        val pathData = transformToPathData(components)
        var code = ""
        code += initialSetup()
        code += generateContourGMCode(pathData.contour, line)
        code += generateDrillGmCode(components,line)
        code += endPartCode()
        return code
    }

}