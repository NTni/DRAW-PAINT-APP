package com.example.draw;
import android.graphics.Path;

public class Draw
{
    public int color;
    public int strokeWidth;
    public Path path;

    public Draw(int color,int strokeWidth,Path path)
    {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public Path getPath() {
        return path;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
