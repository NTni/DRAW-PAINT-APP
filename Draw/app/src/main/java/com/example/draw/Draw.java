package com.example.draw;
import android.graphics.Path;

public class Draw
{
    public int color;
    public int strokeWidth;
    public Path path;
    public boolean emboss;
    public boolean blur;
    public boolean erase;


    public Draw(int color, int strokeWidth, Path path, boolean emboss, boolean blur)
    {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
        this.blur = blur;
        this.emboss = emboss;
    }

    public boolean isEmboss() {
        return emboss;
    }

    public boolean isBlur() {
        return blur;
    }

    public boolean isErase() {
        return erase;
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

    public void setEmboss(boolean emboss) {
        this.emboss = emboss;
    }

    public void setErase(boolean erase) {
        this.erase = erase;
    }

    public void setBlur(boolean blur) {
        this.blur = blur;
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
