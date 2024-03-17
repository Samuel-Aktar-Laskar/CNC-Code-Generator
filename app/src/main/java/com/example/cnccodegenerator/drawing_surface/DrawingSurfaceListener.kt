package com.example.cnccodegenerator.drawing_surface

import android.graphics.Path
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.example.cnccodegenerator.drawing.shapes.Line
import kotlin.math.hypot

private const val TAG = "DrawingSurfaceListener"
class DrawingSurfaceListener(
    private val callback :DrawingSurfaceListenerCallback,
    private val perspective: Perspective
) : OnTouchListener {
    private var pointerDistance = 0f
    private var pointerX=0f
    private var pointerY=0f




    private fun calculatePointerDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return hypot(x, y)
    }

    private fun handleActionMove( event: MotionEvent) {

        callback.end_changed(event.x, event.y)
        Log.d(TAG, "On Move ${pointerX}, ${pointerY}")
        if (event.pointerCount == 1) {

            val dx = (event.getX(0) - pointerX)/perspective.scale
            val dy = (event.getY(0) - pointerY)/perspective.scale
            pointerX = event.getX(0)
            pointerY = event.getY(0)
            callback.translatePerspective(dx,dy)

        } else {
            val pointerDistanceOld = pointerDistance
            pointerDistance = calculatePointerDistance(event)
            if (pointerDistanceOld > 0 && pointerDistanceOld != pointerDistance) {
                val scale = pointerDistance / pointerDistanceOld
                callback.multiplyPerspectiveScale(scale)
            }
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val drawingSurface = view as DrawingSurface

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                pointerX=event.getX(0)
                pointerY=event.getY(0)
                callback.start_changed(event.x, event.y)
                Log.d(TAG, "onTouch: ${event.actionIndex} px py ${pointerX}, ${pointerY}")
            }
            MotionEvent.ACTION_POINTER_DOWN->{
                Log.d(TAG, "onPointerDown: ${event.actionIndex}")
                pointerDistance = calculatePointerDistance(event)
            }
            MotionEvent.ACTION_MOVE -> handleActionMove(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                pointerDistance = 0f
                callback.touch_up()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                if (event.actionIndex == 0) {
                    pointerX = event.getX(1)
                    pointerY = event.getY(1)
                }
                else {
                    pointerX = event.getX(0)
                    pointerY = event.getY(0)
                }
                Log.d(TAG, "pointerUp: ${event.actionIndex} and 0: ${event.getX(0)} ${event.getY(0)} 1: ${event.getX(1)} ${event.getY(1)}")
            }
        }

        drawingSurface.refreshDrawingSurface()
        return true
    }


    interface DrawingSurfaceListenerCallback {
        fun multiplyPerspectiveScale(factor: Float)
        fun translatePerspective(x: Float, y: Float)

        fun start_changed(x:Float, y:Float)
        fun end_changed(x:Float, y:Float)
        fun touch_up()
    }

}