package com.example.cnccodegenerator.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.cnccodegenerator.R
import com.example.cnccodegenerator.databinding.FragmentCodeViewBinding
import java.io.File


const val ARG_CODE_FILE_PATH = "arg_code_file_path"

private const val TAG = "CodeView"
class CodeView : AppCompatActivity()  {

    private lateinit var codeFile : File
    private lateinit var tvCodeView: TextView
    private lateinit var binding: FragmentCodeViewBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCodeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tvCodeView = binding.tvCode
        toolbar = binding.myToolbar
        setSupportActionBar(toolbar)
        if (intent.hasExtra(ARG_CODE_FILE_PATH)){
           val filePath = intent.getStringExtra(ARG_CODE_FILE_PATH)
            codeFile = File(filePath)
            val fileContent = codeFile.readText()
            tvCodeView.text = fileContent
        }
        else {
            Log.e(TAG, "onCreate: No args provided ", )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.code_view_menu,menu)
        return true
    }
    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.actionCopyContent ->{
                copyToClipboard(this, tvCodeView.text.toString())
                Toast.makeText(this, "Code Copied", Toast.LENGTH_SHORT).show()
               true 
            }
            else -> super.onOptionsItemSelected(item)
        } 
    }
}