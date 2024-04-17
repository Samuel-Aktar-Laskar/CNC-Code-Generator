package com.example.cnccodegenerator.code_generator

import android.graphics.Point
import android.graphics.PointF
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing.shapes.Arc
import com.example.cnccodegenerator.drawing.shapes.Line
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt




fun min(vararg x : Float) : Float{
   var res = Float.MAX_VALUE
    for (tmp in x){
        res = min(res, tmp)
    }
    return res
}

class Node(val startPoint: PointF, val endPoint: PointF, val index : Int) {
}

fun makeAdjGraph(components: List<Node>): List<MutableList<Int>> {
    val n = components.size
    val adj = List<MutableList<Int>>(n){ mutableListOf() }

    for( i in 0 until n){
        val node = components[i]
        for(j in 0 until n){
            if (i == j) continue
            val child = components[j]
            val d1 = node.endPoint.distance(child.endPoint)
            val d2 = node.endPoint.distance(child.startPoint)
            val d3 = node.startPoint.distance(child.endPoint)
            val d4 = node.startPoint.distance(child.startPoint)
            val mnD = min(d1,d2,d3,d4)
            if (mnD < epsilon){
                adj[i].add(j);
            }
        }
    }
    return adj;
}

fun dfs(
    node: Int,
    parentNode: Int,
    adj: List<MutableList<Int>>,
    nodesVisited: MutableList<Boolean>,
    gNodesVisited: MutableList<Boolean>,
    path: MutableList<Int>
): Boolean{

    if (nodesVisited[node]) return true
    nodesVisited[node] = true;
    if (gNodesVisited[node]) return false


    for(child in adj[node]){
        if (child == parentNode) continue;
        if (dfs(child,node,adj,nodesVisited,gNodesVisited,path)){
            path.add(node)
            return true
        }
    }
    return false
}
fun extractClosedLoops(components : List<Shape>, nodesDone : MutableList<Boolean> ): MutableList<List<Node>> {
    val  n = components.size
    val transformedComponent = mutableListOf<Node>()
    for(i in 0 until n){
        val item = components[i]
        if (item !is Line) continue
        val node = Node(PointF(item.x1,item.y1), PointF(item.x2, item.y2), i);
        transformedComponent.add(node)
    }
    val m = transformedComponent.size
    val gNodesVisited = MutableList<Boolean>(m){false}
    val adj = makeAdjGraph(transformedComponent)

    val loopComponents = mutableListOf<List<Node>>()
    for(node in 0 until m){
        if (gNodesVisited[node]) continue;
        val nodesVisited = MutableList<Boolean>(m){false}
        val path = mutableListOf<Int>()
        if (dfs(node,-1,adj,nodesVisited,gNodesVisited,path)){
            loopComponents.add(path.map { transformedComponent[it] })
        }
        for(element in path){
            gNodesVisited[element] = true
            nodesDone[transformedComponent[element].index] = true
        }
    }
    return loopComponents
}

