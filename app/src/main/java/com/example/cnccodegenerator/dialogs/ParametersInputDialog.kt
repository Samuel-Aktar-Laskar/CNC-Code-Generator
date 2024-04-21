package com.example.cnccodegenerator.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cnccodegenerator.R

class ParametersInputDialog(
    val positiveCallback : (
            programTitle: String,

            )->Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_save_file,null)
        return AlertDialog.Builder(requireActivity())
            .setTitle("hi")
            .setPositiveButton("Compile") { _, _ ->
            }
            .create()
    }
}