package com.example.cnccodegenerator.drawing_surface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.Util.forn
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.DrawingSurfaceThread
import com.example.cnccodegenerator.drawing.Shape
import java.lang.Math.ceil
import kotlin.math.ceil

private const val TAG = "Drawing Surface"
open class DrawingSurface : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrSet: AttributeSet?) : super(context, attrSet)

    private val gridSize = 1.cm() // Size of each grid cell
    private val gridColor = Color.LTGRAY // Color of the grid lines
    private val gridWidth = 4 // Width of the grid
    private var drawingThread: DrawingSurfaceThread? = null



    private val drawingSurfaceListener : DrawingSurfaceListener
    private val perspective : Perspective



    private var surfaceLock: Object? = null
    private var surfaceDirty = false
    private var surfaceReady = false

    private var components = mutableListOf<Shape>()


    fun setComponents(components : MutableList<Shape>){
        this.components = components
    }

    init {
        surfaceLock = Object()
        perspective = Perspective()
        val callback : DrawingSurfaceListener.DrawingSurfaceListenerCallback = object : DrawingSurfaceListener.DrawingSurfaceListenerCallback {
            override fun multiplyPerspectiveScale(factor: Float) {
                Log.d(TAG, "multiplyPerspectiveScale: $factor")
                perspective.multiplyScale(factor)
            }

            override fun translatePerspective(dx: Float, dy: Float) {
                perspective.translate(dx,dy)
            }

        }
        drawingSurfaceListener= DrawingSurfaceListener(callback, perspective)
        setOnTouchListener(drawingSurfaceListener)

    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        holder.addCallback(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        holder.removeCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Start the drawing thread
        drawingThread?.stop()
        drawingThread = DrawingSurfaceThread(this, DrawLoop())
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Surface has changed
        surfaceReady = true
        drawingThread?.start()
        refreshDrawingSurface()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Stop the drawing thread
        surfaceReady = false
        drawingThread?.stop()
    }



    fun refreshDrawingSurface() {
        surfaceLock?.let {
            synchronized(it) {
                surfaceDirty = true
                it.notify()
            }
        }
    }

    private fun drawGrid(canvas: Canvas?) {
        perspective.applyToCanvas(canvas!!)
        // Get the size of the surface
        val width = canvas?.width ?: return
        val height = canvas.height

        val originY = height / 2f
        val originX = width / 7f

        val scaleFactor = canvas.matrix.mapRadius(gridSize)

        // Calculate the number of cells needed to fill the screen
        val screenWidth = canvas.width / scaleFactor
        val screenHeight = canvas.height / scaleFactor
        var numColumns = 500
        val numRows = 500

        // Draw the vertical grid lines
        var x = originX
        forn(numColumns) {
            canvas.drawLine(x, (-300).cm(), x, 300.cm(), getPaint())
            x += gridSize
        }
        x = originX
        forn(numColumns) {
            canvas.drawLine(x.toFloat(), (-300).cm(), x.toFloat(), 300.cm(), getPaint())
            x -= gridSize
        }

        // Draw the horizontal grid lines
        var y = originY
        forn(numRows) {
            canvas.drawLine((-300).cm(), y, 300.cm(), y, getPaint())
            y += gridSize
        }
        y = originY
        forn(numRows){
            canvas.drawLine((-300).cm(), y, 300.cm(), y, getPaint())
            y -= gridSize
        }

        //drawing center line
        val paint = Paint()
        paint.color = Color.GRAY
        paint.strokeWidth=4f/perspective.scale
        paint.pathEffect = DashPathEffect(floatArrayOf(80f, 10f, 20f, 10f), 0f)

        canvas.drawLine((-300).cm(), originY,300.cm(),originY, paint )
        canvas.drawLine(originX,(-300).cm(), originX, 300.cm(), Paint().apply { color= Color.GRAY
        strokeWidth = 4f/perspective.scale
        })


        components.forEach {
            it.draw(canvas,originX, originY)
            it.drawReflection(canvas,originX,originY)
        }


    }

    private fun getPaint(): Paint {
        // Create and return a Paint object with the specified color and width
        val paint = Paint()
        paint.color = gridColor
        paint.strokeWidth = gridWidth.toFloat()/perspective.scale
        return paint
    }

    private inner class DrawLoop : Runnable {
        val holder: SurfaceHolder = getHolder()
        override fun run() {
            surfaceLock?.let {
                synchronized(it) {
                    if (!surfaceDirty && surfaceReady) {
                        try {
                            it.wait()
                        } catch (e: InterruptedException) {
                            return
                        }
                    } else {
                        surfaceDirty = false
                    }
                    if (!surfaceReady) {
                        return
                    }
                }
            }

            var canvas: Canvas? = null
            synchronized(holder) {
                try {
                    canvas = holder.lockCanvas()
                    canvas?.drawColor(Color.WHITE)
                    canvas?.let {
                        drawGrid(it)
                    }
                    canvas
                } catch (e: IllegalArgumentException) {
                    Log.e(TAG, "run: ", e)
                } finally {
                    canvas?.let {
                        holder.unlockCanvasAndPost(it)
                    }
                }
            }
        }
    }
}