package com.example.cnccodegenerator.command.commands

import android.graphics.Color
import android.graphics.Point
import com.example.cnccodegenerator.Util
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.command.CommandHandler
import com.example.cnccodegenerator.command.InputType
import com.example.cnccodegenerator.drawing.Shape

class Arc(
    override val updateHint: (hint: String) -> Unit,
    override val onDone: (shape: Shape) -> Unit,
    override val changeInputType: (inputType: InputType) -> Unit
) : CommandHandler {
    private var state = 0
    var x1=0f
    var y1 = 0f
    var x2 = 0f
    var y2 =0f
    var x3= 0f
    var y3=0f

    init {
        updateHint("Enter start coordinates")
        changeInputType(InputType.NUMBER)
    }



    override fun processCommand(
        command: String
    ) {
        when(state) {
            0->{
                val numbers = Util.extractNumbers(command) ?: return
                x1 = numbers.first
                y1 = numbers.second
                updateHint("Enter second coordinates")
                state++
            }
            1->{
                val numbers = Util.extractNumbers(command) ?: return
                x2 = numbers.first
                y2 = numbers.second
                updateHint("Enter end coordinates")
                state++
            }
            2 ->{
                val numbers = Util.extractNumbers(command) ?: return
                x3=numbers.first
                y3 = numbers.second

                val component = com.example.cnccodegenerator.drawing.shapes.Arc(
                    Point(x1.cm().toInt(), y1.cm().toInt()),
                    Point(x2.cm().toInt(), y2.cm().toInt()),
                    Point(x3.cm().toInt(), y3.cm().toInt()),
                )
                component.setStrokeWidth(0.05f)
                component.setStrokeColor(Color.DKGRAY)
                onDone(component)
            }

        }
    }
}