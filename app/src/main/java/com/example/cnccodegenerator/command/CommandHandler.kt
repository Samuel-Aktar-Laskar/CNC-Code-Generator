package com.example.cnccodegenerator.command

import com.example.cnccodegenerator.drawing.Shape

interface CommandHandler {

    var updateHint: (hint: String) -> Unit
    var onDone: (shape: Shape) -> Unit

    fun processCommand(command:String)

}