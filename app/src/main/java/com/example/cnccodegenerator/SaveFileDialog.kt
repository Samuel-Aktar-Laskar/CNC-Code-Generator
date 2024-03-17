package com.example.cnccodegenerator

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class SaveFileDialog : DialogFragment() {

    private lateinit var listener: SaveDialogListener

    interface SaveDialogListener {
        fun onSaveClicked(fileName: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as SaveDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SaveDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val inflater: LayoutInflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_save_file, null)

        val fileNameEditText: EditText = view.findViewById(R.id.editTextFileName)

        return AlertDialog.Builder(requireActivity())
            .setTitle("Save File")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                val fileName = fileNameEditText.text.toString()
                listener.onSaveClicked(fileName)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
    }
}
