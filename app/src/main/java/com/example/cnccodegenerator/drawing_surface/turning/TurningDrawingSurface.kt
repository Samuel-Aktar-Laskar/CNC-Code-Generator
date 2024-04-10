package com.example.cnccodegenerator.drawing_surface.turning

import android.content.Context
import android.util.AttributeSet
import com.example.cnccodegenerator.drawing_surface.DrawingSurface


private const val TAG = "DrawingSurface"
open class TurningDrawingSurface : DrawingSurface {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrSet: AttributeSet?) : super(context, attrSet)
}