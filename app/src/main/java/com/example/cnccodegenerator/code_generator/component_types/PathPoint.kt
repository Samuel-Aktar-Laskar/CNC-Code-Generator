package com.example.cnccodegenerator.code_generator.component_types

class PathPoint {
    var x: Float
    var y: Float
    var isFillet: Boolean

    constructor() {
        x = 0f
        y = 0f
        isFillet = false
    }

    constructor(x: Float, y: Float, isFillet: Boolean = false) {
        this.x = x
        this.y = y
        this.isFillet = isFillet
    }
}
