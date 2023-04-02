package com.example.cnccodegenerator.command.commands

import android.graphics.Color
import android.util.Log
import com.example.Util
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.command.CommandHandler
import com.example.cnccodegenerator.drawing.Shape


private const val TAG = "Line"
class Line(
    override var updateHint: (hint: String) -> Unit,
    override var onDone: (shape: Shape) -> Unit
) : CommandHandler{
    var state = 0
    var x1=0f
    var y1 = 0f
    var x2=0f
    var y2 = 0f

    init {
        updateHint("Enter initial coordinates")
    }



    override fun processCommand(
        command: String
    ) {
        when(state) {
            0->{
                val numbers = Util.extractNumbers(command) ?: return
                x1 = numbers.first
                y1 = numbers.second
                updateHint("Enter final coordinates")
                state++
            }
            1 ->{
                val numbers = Util.extractNumbers(command) ?: return
                x2=numbers.first
                y2 = numbers.second
                Log.d(TAG, "processCommand: x2 and y2 are $x2 $y2")

                val component = com.example.cnccodegenerator.drawing.shapes.Line(
                    x1.cm(),
                    y1.cm(),
                    x2.cm(),
                    y2.cm()
                )
                component.setStrokeWidth(0.1f)
                component.setStrokeColor(Color.MAGENTA)
                onDone(component)
            }

        }
    }
}