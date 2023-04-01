package com.example.cnccodegenerator.command

import android.widget.EditText
import com.example.cnccodegenerator.command.commands.Line
import com.example.cnccodegenerator.drawing.Shape

class CommandManager(
    private val etCommand : EditText,
    private val components : MutableList<Shape>,
    private val refreshDrawingSurface: ()->Unit
) {
    private var state = 0

    private lateinit var commandHandler : CommandHandler

    private val updateHint = { hint:String ->
        etCommand.hint = hint
        etCommand.setText("")
    }

    private val onDone = {component: Shape ->
        components.add(component)
        etCommand.hint = "Enter Query"
        etCommand.setText("")
        etCommand.clearFocus()
        state = 0
        refreshDrawingSurface()
    }

    private fun processQuery(command: String) : Boolean{
        when(command.lowercase()) {
            "line" -> {
                commandHandler = Line(updateHint, onDone)
                return true
            }
        }
        return false
    }

    fun processCommand(command : String) : Boolean{
        when (state) {
            0 -> {
                if (processQuery(command))
                    state++
                else etCommand.setText("")
            }
            1 -> {
                commandHandler.processCommand(command)
            }
        }
        return false
    }
}