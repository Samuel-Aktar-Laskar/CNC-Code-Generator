package com.example.cnccodegenerator.code_generator

import android.content.Context
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cnccodegenerator.SaveFileDialog
import com.example.cnccodegenerator.databinding.DialogSaveFileBinding
import java.io.File
import java.nio.file.Path
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


const val epsilon= 0.0001f

fun PointF.distance(end : PointF) : Float {
    return sqrt(  abs(this.x - end.x).pow(2) + abs(this.y-end.y).pow(2))
}


fun PointF.overlap(end: PointF): Boolean {
    return this.distance(end) < epsilon
}

class IntWrapper(value: Int) {
    var value : Int = 0
    fun incre(amount : Int = 1): Int {
        return value++
    }
}

fun String.saveToFile(context: AppCompatActivity, dirName : String){
    fun actionBtnClick(fileName: String){
        val file = File(
            context.filesDir,
            "/$dirName/$fileName.txt"
        )
        file.writeText(this)
    }
    val dialog = SaveFileDialog(
        title = "Output file name",
        onClickPositiveButton = {
            actionBtnClick(it)
        }
    )
    dialog.show(context.supportFragmentManager, "Run compile dialog")
    dialog.showsDialog
}