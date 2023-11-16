package com.example.cnccodegenerator

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cnccodegenerator.databinding.ActivityHomeBinding
import java.io.File
import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cnccodegenerator.models.JsonFile
import com.example.cnccodegenerator.recycler_view_adapters.SketcherJsonFilesAdapter


private const val TAG = "koka kola"
class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val permission = Manifest.permission.MANAGE_EXTERNAL_STORAGE
    private  val requestCode = 3412
    private lateinit var recyclerView: RecyclerView
    private var jsonFiles = listOf<JsonFile>()
    private lateinit var folder: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        folder = File(getExternalFilesDir(null), Constants.DIRECTORY_NAME)

        setSupportActionBar(findViewById(R.id.toolbar))
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val intent = Intent(this, Sketcher::class.java)
            startActivity(intent)
        }


        val permissionGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
        }
        Log.d(TAG, "onCreate: folder path ${folder.path}")



        getJsonFilesInFolder() // Replace with your folder path
        val adapter = SketcherJsonFilesAdapter(jsonFiles)
        recyclerView.adapter = adapter
    }
    private fun getJsonFilesInFolder() {
        // Implement logic to list JSON files in the specified folder
        // You can use File API or any other method to get the list of files

        if (!folder.exists()){
            val x = folder.mkdir()
            Toast.makeText(this, "Directory " + (if (x) "created" else "not created"), Toast.LENGTH_SHORT).show()

        }
        jsonFiles= folder.listFiles { _, name -> name.endsWith(".json") }
            ?.map { JsonFile(it.name, it.path) }
            ?: emptyList()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            // Check if the permission is granted
            requestCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted, proceed with file operations
                    if (!folder.exists()) {
                        val x = folder.mkdirs()
                        Toast.makeText(this, "Directory " + (if (x) "created" else "not created"), Toast.LENGTH_SHORT).show()
                    }

                } else {
                    // Permission denied, handle accordingly
                    finish()
                }
                return
            }
            else -> {
                // Handle other permissions if needed
            }
        }
    }

}