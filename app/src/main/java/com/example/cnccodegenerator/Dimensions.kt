package com.example.cnccodegenerator

object Dimensions {
    const val CENTIMETER = 80

    fun <T : Number> T.cm(): Float {
        return this.toFloat() * CENTIMETER
    }
}