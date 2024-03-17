package com.example.cnccodegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import com.example.cnccodegenerator.command.CommandManager
import com.example.cnccodegenerator.databinding.ActivityMainBinding
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing_surface.DrawingSurface
import com.example.cnccodegenerator.scene_graph_persistence_manager.SceneGraphJsonSerializer
import java.io.File

private const val TAG = "Sketcher"
class Sketcher : AppCompatActivity() , SaveFileDialog.SaveDialogListener{
    private lateinit var binding: ActivityMainBinding

    private lateinit var btEsc : Button
    private lateinit var btEnter: Button
    private lateinit var btn_line : Button
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
        btn_line = binding.btLine

        //toolbar
        val toolbar = binding.myToolbar
        setSupportActionBar(toolbar)




        sketcher.setComponents(components)

        val commandManager = CommandManager(etCommand,components){
            sketcher.refreshDrawingSurface()
        }

        btEnter.setOnClickListener {

            commandManager.processCommand(
                etCommand.text.toString()
            )
        }
        btn_line.setOnClickListener{
            sketcher.toggle_draw_line()
        }
        binding.btnUndo.setOnClickListener{
            if (components.isNotEmpty())
                components.removeLast()

            sketcher.refreshDrawingSurface()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sketcher_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save->{
                Log.d(TAG, "onCreate: CLicked on save button")

                val saveFileDialog = SaveFileDialog()
                saveFileDialog.show(supportFragmentManager, "SaveFileDialog")
                saveFileDialog.showsDialog
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveClicked(fileName: String) {
        val serializer = SceneGraphJsonSerializer(components)
        serializer.Serialize()
        serializer.saveSceneGraphToFile(File(getExternalFilesDir(null),"/${Constants.DIRECTORY_NAME}/${fileName}.json"))

    }
}