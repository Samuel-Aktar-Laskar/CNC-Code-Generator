package com.example.cnccodegenerator

import android.graphics.Point
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cnccodegenerator.code_generator.CodeGenerator
import com.example.cnccodegenerator.code_generator.extractClosedLoops
import com.example.cnccodegenerator.drawing.shapes.Arc
import com.example.cnccodegenerator.drawing.shapes.Line

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
private const val TAG = "ExampleInstrumentedTest"
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val components = listOf(
            Line(0f,0f,10f,0f), Line(10f,0f,10f,10f),
            Line(0f,10f,10f,10f), Arc(Point(), Point(), Point()), Line(0f,0f,0f,10f)
            , Line(1f,1f,3f,3f)
        );

        val nodesDone = MutableList(components.size){false}
        val loopComponents = extractClosedLoops(components,nodesDone)
        for(comp in loopComponents){
            var x = ""
            for (d in comp){
               x = x + " " + d.index
            }
            Log.d(TAG, "Row " + x)
        }
        var done = ""
        for(x in nodesDone){
            done = done + " " + x;
        }
        Log.d(TAG, "Nodes done " + done)
        assert(true)
    }

    @Test
    fun generateCode(){
        val components = listOf(
            Line(0f,0f,10f,0f), Line(10f,0f,10f,10f),
            Line(0f,10f,10f,10f), Arc(Point(), Point(), Point()), Line(0f,0f,0f,10f)
            , Line(1f,1f,3f,3f)
        );
        val codegen = CodeGenerator()
        val res = codegen.generateGMCode(components)
        Log.d(TAG, "generateCode: CODE : $res")

    }

}