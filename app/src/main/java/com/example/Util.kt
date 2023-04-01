package com.example

object Util {

    fun extractNumbers(input: String): Pair<Float, Float>? {
        val parts = input.split(",")
        if (parts.size != 2) {
            return null
        }
        val num1 = parts[0].toFloatOrNull() ?: return null
        val num2 = parts[1].toFloatOrNull() ?: return null
        return num1 to num2
    }

}