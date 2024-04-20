package com.example.cnccodegenerator

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class SaveFileDialog(
    private var title: String = "Save File",
    private var message: String = "",
    private var positiveButtonText: String = "Save",
    private var negativeButtonText: String = "Cancel",
    private val onClickPositiveButton : (String)->Unit = {}
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val inflater: LayoutInflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_save_file, null)

        val fileNameEditText: EditText = view.findViewById(R.id.editTextFileName)

        return AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setView(view)
            .setPositiveButton(positiveButtonText) { _, _ ->
                val fileName = fileNameEditText.text.toString()
                onClickPositiveButton(fileName)
            }
            .setMessage(message)
            .setNegativeButton(negativeButtonText) { _, _ -> }
            .create()
    }
}
