package com.example.cnccodegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import com.example.cnccodegenerator.command.CommandManager
import com.example.cnccodegenerator.databinding.ActivityMainBinding
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing_surface.DrawingSurface

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var btEsc : Button
    private lateinit var btEnter: Button
    private lateinit var etCommand : EditText
    private lateinit var sketcher : DrawingSurface

    private val components = mutableListOf<Shape>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btEsc = binding.btEsc
        btEnter = binding.btEnter
        etCommand = binding.etCommand
        sketcher = binding.sketcher12



        sketcher.setComponents(components)

        val commandManager = CommandManager(etCommand,components){
            sketcher.refreshDrawingSurface()
        }

        btEnter.setOnClickListener {

            commandManager.processCommand(
                etCommand.text.toString()
            )
        }

        etCommand.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Perform some action when the Enter button is clicked
                btEnter.performClick()
                true
            } else {
                false
            }
        }




    }
}