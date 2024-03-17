package com.example.cnccodegenerator.scene_graph_persistence_manager


import com.example.cnccodegenerator.drawing.Shape
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter

class SceneGraphJsonSerializer(private val components: List<Shape>) {
    private lateinit var jsonObject: JSONObject
    fun Serialize(){
        jsonObject = JSONObject()
        jsonObject.put("name", "root")
        jsonObject.put("dimensions", "will add later")
        jsonObject.put("shapes", serializeShapes())

    }
    fun saveSceneGraphToFile(file: File) {
        try {
            val writer = FileWriter(file)
            writer.use {
                it.write(jsonObject.toString(4)) // Use indentation of 4 spaces for better readability
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun serializeShapes(): JSONObject {
        val shapes = JSONObject()
        shapes.put("lines", serializeLines())
        shapes.put("arc", serializeArcs());
        shapes.put("arrow", serializeArrows())
        return shapes
    }

    private fun serializeLines(): JSONArray {
        val linesArray = JSONArray()

        linesArray.put(JSONObject().apply { put("start", JSONObject().apply {
            put("x",0)
            put("y", 0)
        })}
        )

        return linesArray
    }

    private fun serializeArcs(): JSONArray {
        val arcsArray = JSONArray()

        // Add arc instances to the array

        return arcsArray
    }

    private fun serializeArrows(): JSONArray {
        val arrowsArray = JSONArray()

        // Add arrow instances to the array

        return arrowsArray
    }
}