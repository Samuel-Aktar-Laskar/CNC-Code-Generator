package com.example.cnccodegenerator.scene_graph_persistence_manager

import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing.shapes.Line
import org.json.JSONObject
import java.io.File

class SceneGraphJsonDeserializer constructor(private val jsonFile: File){

    private fun loadJson() : JSONObject{
        val jsonString = jsonFile.readText()
        return JSONObject(jsonString)
    }

    fun getComponents(): List<Shape>{
        val json = loadJson()
        val shapes : JSONObject = json.getJSONObject("shapes")
        val lines = shapes.getJSONArray("lines")

        val components  = mutableListOf<Shape>()
        for(i in 0 until lines.length()){
            val obj = lines.getJSONObject(i)
            val start = obj.getJSONObject("start")
            val end = obj.getJSONObject("end")
            val x1 = start.getDouble("x")
            val y1 = start.getDouble("y")
            val x2 = end.getDouble("x")
            val y2 = end.getDouble("y")
            components.add(Line(x1.toFloat(),y1.toFloat(),x2.toFloat(),y2.toFloat()))
        }

        return components
    }
}