package com.example.cnccodegenerator.drawing_surface

import android.graphics.PointF
import com.example.cnccodegenerator.Dimensions.cm

private fun PointF.getComp() : List<Float> {
    return listOf(this.x, this.y)
}

private fun Float.between(x1 : Float, x2 : Float) : Boolean {
   return this in x1..x2
}
fun snapCoordinates(end: PointF ): PointF {
    val (x1,y1) = end.getComp()
    val sig_x = if (x1>0) 1 else -1
    val sig_y = if (y1>0) 1 else -1

    val d = 1f.cm()
    val x2 = x1 - x1%d
    val y2 = y1 - y1%d
    val x3 = x2 + sig_x*d/2f
    val y3 = y2 + sig_y*d/2f
    val x4 = x2 + sig_x*d
    val y4 = y2 + sig_x*d

    val x = if (x1.between(x2,x3)) x2 else x4
    val y = if (y1.between(y2,y3)) y2 else y4
    return PointF(x,y)
}