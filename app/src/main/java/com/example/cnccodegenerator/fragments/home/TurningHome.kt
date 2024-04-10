package com.example.cnccodegenerator.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cnccodegenerator.Constants
import com.example.cnccodegenerator.R
import com.example.cnccodegenerator.Sketcher
import com.example.cnccodegenerator.models.JsonFile
import com.example.cnccodegenerator.recycler_view_adapters.SketcherJsonFilesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TurningHome : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var drawing_folder : File
    private var jsonFiles = listOf<JsonFile>()
    private lateinit var fab : FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_turning_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recyclerView = view.findViewById(R.id.recyclerView)
        fab.setOnClickListener { view ->
            val intent = Intent(requireContext(), Sketcher::class.java)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SketcherJsonFilesAdapter(requireContext(),jsonFiles)
        recyclerView.adapter = adapter

        drawing_folder = File(requireActivity().filesDir,  Constants.TURNING_DRAWING_DIRECTORY)
        getJsonFilesInFolder()

    }
    private fun getJsonFilesInFolder() {

        if (!drawing_folder.exists()){
            val x = drawing_folder.mkdirs()
            Toast.makeText(context, "Directory " + (if (x) "created" else "not created"), Toast.LENGTH_SHORT).show()
        }
        jsonFiles= drawing_folder.listFiles { _, name -> name.endsWith(".json") }
            ?.map { JsonFile(it.name, it.path) }
            ?: emptyList()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TurningHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}