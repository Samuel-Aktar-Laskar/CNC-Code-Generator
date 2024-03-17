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
import com.example.cnccodegenerator.Util.forn
import com.example.cnccodegenerator.Dimensions.cm
import com.example.cnccodegenerator.DrawingSurfaceThread
import com.example.cnccodegenerator.drawing.Shape
import com.example.cnccodegenerator.drawing.shapes.Line

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
    private var line_path = Line()
    private var draw_line = false
    fun toggle_draw_line(){
        draw_line = true
        refreshDrawingSurface()
    }


    fun setComponents(components : MutableList<Shape>){
        this.components = components
    }

    init {
        surfaceLock = Object()
        perspective = Perspective()
        line_path.setPerspective(perspective)
        line_path.setSolidColor(Color.DKGRAY)
        line_path.setStrokeWidth(0.05f)

        val callback : DrawingSurfaceListener.DrawingSurfaceListenerCallback = object : DrawingSurfaceListener.DrawingSurfaceListenerCallback {
            override fun multiplyPerspectiveScale(factor: Float) {
                Log.d(TAG, "multiplyPerspectiveScale: $factor")
                perspective.multiplyScale(factor)
            }

            override fun translatePerspective(dx: Float, dy: Float) {
                if (!draw_line)
                    perspective.translate(dx,dy)
            }

            override fun start_changed(x: Float, y: Float) {
                line_path.set_start(x,y)
            }

            override fun end_changed(x: Float, y: Float) {
                line_path.set_end(x,y)
            }

            override fun touch_up() {
                if (draw_line) {

                    components.add(line_path.copy())
                }
//                    components.add(Line(line_path.x1_trans, line_path.y1_trans, line_path.x2_trans, line_path.y2_trans))
                draw_line = false
                
                line_path.reset_line()
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
        val width = canvas!!.width
        val height = canvas.height
        val originY = height / 2f
        val originX = width / 7f
        line_path.setOrigin(originX,originY)
        
        perspective.applyToCanvas(canvas!!)
        // Get the size of the surface





        var numColumns = 300
        val numRows = 300

        // Draw the vertical grid lines
        var x = originX
        forn(numColumns) {
            canvas.drawLine(x, (-300).cm(), x, 300.cm(), getPaint())
            canvas.drawText(it.toString(), x - 0.4.cm()/perspective.scale, originY+0.5.cm()/perspective.scale , Paints.numberingPaint.apply {
                textSize=0.5.cm()/perspective.scale
            })
            x += gridSize
        }
        x = originX-gridSize
        forn(numColumns) {
            canvas.drawLine(x, (-300).cm(), x, 300.cm(), getPaint())
            canvas.drawText("-${it+1}", x - 0.4.cm()/perspective.scale, originY+0.5.cm()/perspective.scale , Paints.numberingPaint.apply {
                textSize=0.5.cm()/perspective.scale
            })
            x -= gridSize
        }

        // Draw the horizontal grid lines
        var y = originY+gridSize
        forn(numRows) {
            canvas.drawLine((-300).cm(), y, 300.cm(), y, getPaint())

            canvas.drawText("${it+1}", originX-0.4.cm()/perspective.scale, y+0.5.cm()/perspective.scale , Paints.numberingPaint.apply {
                textSize=0.5.cm()/perspective.scale
            })
            y += gridSize
        }
        y = originY-gridSize
        forn(numRows){
            canvas.drawLine((-300).cm(), y, 300.cm(), y, getPaint())
            canvas.drawText("${it+1}", originX-0.4.cm()/perspective.scale, y+0.5.cm()/perspective.scale , Paints.numberingPaint.apply {
                textSize=0.5.cm()/perspective.scale
            })
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
        canvas.drawCircle(0f,0f,0.2.cm(),Paint().apply { color=Color.RED })


        Log.d(TAG, "drawGrid: Perspectie scale is ${perspective.scale}")
        if (draw_line)
            line_path.draw_normal(canvas)

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