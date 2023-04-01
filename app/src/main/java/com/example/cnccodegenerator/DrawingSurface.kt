package com.example.cnccodegenerator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView

class DrawingSurface(context: Context, attrs: AttributeSet?) : SurfaceView(context), SurfaceHolder.Callback, Runnable {

    private val gridSize = 50 // Size of each grid cell
    private val gridColor = Color.LTGRAY // Color of the grid lines
    private val gridWidth = 2 // Width of the grid

    private var drawingThread: Thread? = null
    private var isRunning = false
    private var surfaceHolder: SurfaceHolder? = null

    init {
        // Set up the SurfaceHolder callback
        surfaceHolder = holder
        surfaceHolder?.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Start the drawing thread
        isRunning = true
        drawingThread = Thread(this)
        drawingThread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Surface has changed
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Stop the drawing thread
        isRunning = false
        drawingThread?.join()
    }

    override fun run() {
        while (isRunning) {
            // Lock the canvas and get a reference to the canvas object
            val canvas = surfaceHolder?.lockCanvas()

            // Draw the grid lines
            canvas?.drawColor(Color.WHITE)
            drawGrid(canvas)
            // Unlock the canvas and post it to the surface
            surfaceHolder?.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawGrid(canvas: Canvas?) {
        // Get the size of the surface
        val width = canvas?.width ?: return
        val height = canvas.height

        // Draw the vertical grid lines
        var x = 0
        while (x < width) {
            canvas.drawLine(x.toFloat(), 0f, x.toFloat(), height.toFloat(), getPaint())
            x += gridSize
        }

        // Draw the horizontal grid lines
        var y = 0
        while (y < height) {
            canvas.drawLine(0f, y.toFloat(), width.toFloat(), y.toFloat(), getPaint())
            y += gridSize
        }
    }

    private fun getPaint(): Paint {
        // Create and return a Paint object with the specified color and width
        val paint = Paint()
        paint.color = gridColor
        paint.strokeWidth = gridWidth.toFloat()
        return paint
    }
}