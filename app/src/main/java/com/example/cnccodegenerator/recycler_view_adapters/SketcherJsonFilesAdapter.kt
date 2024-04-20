package com.example.cnccodegenerator.recycler_view_adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cnccodegenerator.R
import com.example.cnccodegenerator.Sketcher
import com.example.cnccodegenerator.activities.MillingSketcher
import com.example.cnccodegenerator.models.JsonFile

private const val TAG = "SketcherJsonFilesAdapte"
class SketcherJsonFilesAdapter(
    private val context: Context,
    private val jsonFiles: MutableList<JsonFile>,
    private val isMilling : Boolean
): RecyclerView.Adapter<SketcherJsonFilesAdapter.ViewHolder>() {

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
        holder.itemView.setOnClickListener{
            val intent = Intent(context, if (isMilling) MillingSketcher::class.java else Sketcher::class.java)
            intent.putExtra("file-path",jsonFile.filePath)
            context.startActivity(intent)
            Log.d(TAG, "onBindViewHolder: On click in adapter")
        }
    }

    override fun getItemCount(): Int {
        return jsonFiles.size
    }
}