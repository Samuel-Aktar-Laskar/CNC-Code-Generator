package com.example.cnccodegenerator.recycler_view_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cnccodegenerator.R
import com.example.cnccodegenerator.models.JsonFile

class SketcherJsonFilesAdapter(private val jsonFiles: List<JsonFile>): RecyclerView.Adapter<SketcherJsonFilesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.fileNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_sketcher_file_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jsonFile = jsonFiles[position]
        holder.fileNameTextView.text = jsonFile.fileName
        // Add click listener if needed
    }

    override fun getItemCount(): Int {
        return jsonFiles.size
    }
}