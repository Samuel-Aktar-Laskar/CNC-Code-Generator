package com.example.cnccodegenerator.drawing_surface

import android.graphics.Color
import android.graphics.Paint
import com.example.cnccodegenerator.Dimensions.cm

object Paints {
    val numberingPaint = Paint().apply {
        textSize=0.4.cm()
        color=Color.GRAY
        textAlign=Paint.Align.CENTER
    }
}