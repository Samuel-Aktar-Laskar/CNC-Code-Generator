package com.example.cnccodegenerator.code_generator.component_types;

public class PathPoint {
    public int x;
    public int y;
    public boolean isFillet;
    public PathPoint(){
        x=0;
        y=0;
        isFillet = false;
    }
    public PathPoint(int x, int y, boolean isFillet){
        this.x = x;
        this.y = y;
        this.isFillet = isFillet;
    }

}
