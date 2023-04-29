package com.example.cnccodegenerator.drawing_surface

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.hypot

private const val TAG = "DrawingSurfaceListener"
class DrawingSurfaceListener(
    private val callback :DrawingSurfaceListenerCallback
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
        if (event.pointerCount == 1) {
            val dx = event.getX(0) - pointerX
            val dy = event.getY(0) - pointerY
            callback.translatePerspective(dx,dy)
            pointerX = event.getX(0)
            pointerY = event.getY(0)


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
                Log.d(TAG, "onTouch: ${event.actionIndex}")
            }
            MotionEvent.ACTION_POINTER_DOWN->{
                Log.d(TAG, "onPointerDown: ${event.actionIndex}")
                pointerDistance = calculatePointerDistance(event)
            }
            MotionEvent.ACTION_MOVE -> handleActionMove(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                pointerDistance = 0f
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
    }

}