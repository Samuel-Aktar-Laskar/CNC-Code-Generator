package com.example.cnccodegenerator.command

import com.example.cnccodegenerator.drawing.Shape

interface CommandHandler {

    val updateHint: (hint: String) -> Unit
    val onDone: (shape: Shape) -> Unit
    val changeInputType : (inputType : InputType) -> Unit

    fun processCommand(command:String)

}