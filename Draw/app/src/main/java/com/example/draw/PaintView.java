//package com.example.draw;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.os.Environment;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class PaintView extends View
//{
//    public static int BrushSize = 10;
//    public static final int DefaultColor = Color.RED;
//    public static final int DefaultBGColor = Color.WHITE;
//    private static final float TOUCH_TOLERANCE = 4;
//
//    private float mX,mY;
//    private Path mPath;
//    private Paint mPaint;
//    private int currentColor;
//    private int bgColor = DefaultBGColor;
//    private int strokeWidth;
//    private Bitmap mBitmap;
//    private Canvas mCanvas;
//    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
//
//    private ArrayList<Draw> paths = new ArrayList<>();
//    private ArrayList<Draw> undo = new ArrayList<>();
//
//    public PaintView(Context context)
//    {
//        super(context,null);
//    }
//
//    public PaintView(Context context, AttributeSet attrs)
//    {
//        super(context, attrs);
//
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setColor(DefaultColor);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setXfermode(null);
//        mPaint.setAlpha(0xff);
//    }
//
//    public void initialise(DisplayMetrics displayMetrics)
//    {
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
//
//        currentColor = DefaultColor;
//        strokeWidth = BrushSize;
//
//        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//        canvas.save();
//        mCanvas.drawColor(bgColor);
//
//        for(Draw draw : paths)
//        {
//            mPaint.setColor(draw.color);
//            mPaint.setStrokeWidth(strokeWidth);
//            mPaint.setMaskFilter(null);
//
//            mCanvas.drawPath(draw.path,mPaint);
//        }
//        canvas.drawBitmap(mBitmap,0,0,mBitmapPaint);
//        canvas.restore();
//    }
//    private void touchStart(float x,float y)
//    {
//        mPath = new Path();
//        Draw draw = new Draw(currentColor,strokeWidth,mPath);
//        paths.add(draw);
//        mPath.reset();
//        mPath.moveTo(x,y);
//        mX = x;
//        mY = y;
//    }
//    private void touchMove(float x,float y)
//    {
//        float dx = Math.abs(x-mX);
//        float dy = Math.abs(y-mY);
//
//        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
//        {
//            mPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
//            mX = x;
//            mY = y;
//        }
//    }
//    private void touchUp()
//    {
//        mPath.lineTo(mX,mY);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        float x = event.getX();
//        float y = event.getY();
//
//        switch (event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                touchStart(x,y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                touchUp();
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                touchMove(x,y);
//                invalidate();
//                break;
//        }
//        return true;
//    }
//    public void clear()
//    {
//        bgColor = DefaultBGColor;
//
//        paths.clear();
//        invalidate();
//    }
//    public void undo()
//    {
//        if(paths.size() > 0)
//        {
//            undo.add(paths.remove(paths.size()-1));
//            invalidate();
//        }
//        else
//        {
//            Toast.makeText(getContext(),"NOTHING TO UNDO",Toast.LENGTH_LONG).show();
//        }
//    }
//    public void redo()
//    {
//        if(undo.size() > 0)
//        {
//            paths.add(undo.remove(undo.size()-1));
//            invalidate();
//        }
//        else
//        {
//            Toast.makeText(getContext(),"NOTHING TO REDO",Toast.LENGTH_LONG).show();
//        }
//    }
//    public void setStrokeWidth(int width)
//    {
//        strokeWidth = width;
//    }
//    public void setColor(int color)
//    {
//        currentColor = color;
//    }
//    public void saveImage()
//    {
//        int count = 0;
//
//        File sdDirectory = Environment.getExternalStorageDirectory();
//        File subDirectory = new File(sdDirectory.toString() + "/Pictures/Draw");
//        if(subDirectory.exists())
//        {
//            File[] existing = subDirectory.listFiles();
//            for(File file:existing)
//            {
//                if(file.getName().endsWith(".jpg")||file.getName().endsWith(".png"))
//                {
//                    count++;
//                }
//            }
//        }
//        else
//        {
//            subDirectory.mkdir();
//        }
//        if(subDirectory.exists())
//        {
//            File image = new File(subDirectory,"/DRAWING_"+(count+1) + ".png");
//            FileOutputStream fileOutputStream;
//
//            try
//            {
//                fileOutputStream = new FileOutputStream(image);
//                mBitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
//                fileOutputStream.flush();
//                fileOutputStream.close();
//                Toast.makeText(getContext(),"SAVED",Toast.LENGTH_LONG).show();
//            }
//            catch (FileNotFoundException e)
//            {
//
//            }
//            catch (IOException e)
//            {
//
//            }
//        }
//    }
//}

package com.example.draw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PaintView extends View {

    public static int BRUSH_SIZE = 10;
    public static final int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;

    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    private boolean emboss;
    private boolean blur;

    private MaskFilter mEmboss;
    private MaskFilter mBlur;

    private ArrayList<Draw> paths = new ArrayList<>();
    private ArrayList<Draw> undo = new ArrayList<>();

    private boolean flag  = false;
    private int lastColor;

    public PaintView(Context context) {

        super(context, null);

    }

    public PaintView(Context context, AttributeSet attrs) {

        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);

        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

        mEmboss= new EmbossMaskFilter(new float[] {1,1,1}, .4f,6,3.5f);
        mBlur = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);

    }

    public void initialise (DisplayMetrics displayMetrics) {

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
        lastColor = currentColor;
    }

    @Override

    protected void onDraw(Canvas canvas) {

        canvas.save();
        mCanvas.drawColor(backgroundColor); // WRONG

        for (Draw draw : paths) {

            mPaint.setColor(draw.color); // WRONG
            mPaint.setStrokeWidth(draw.strokeWidth);
            mPaint.setMaskFilter(null);

            if(draw.emboss)
                mPaint.setMaskFilter(mEmboss);
            else if (draw.blur)
                mPaint.setMaskFilter(mBlur);

            mCanvas.drawPath(draw.path, mPaint);

        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();

    }

    private void touchStart (float x, float y) {

        mPath = new Path();

        Draw draw = new Draw(currentColor, strokeWidth, mPath,emboss,blur);
        paths.add(draw);

        mPath.reset();
        mPath.moveTo(x, y);

        mX = x;
        mY = y;

    }

    private void touchMove (float x, float y) {

        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {

            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);

            mX = x;
            mY = y;

        }

    }

    private void touchUp () {

        mPath.lineTo(mX, mY);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;

        }

        return true;

    }

    public void clear () {

        //backgroundColor = DEFAULT_BG_COLOR;
        //for only white background

        paths.clear();
        invalidate();

    }

    public void undo () {

        if (paths.size() > 0) {

            undo.add(paths.remove(paths.size() - 1));
            invalidate(); // add

        } else {

            Toast.makeText(getContext(), "Nothing to undo", Toast.LENGTH_LONG).show();

        }

    }

    public void redo () {

        if (undo.size() > 0) {

            paths.add(undo.remove(undo.size() - 1));
            invalidate(); // add

        } else {

            Toast.makeText(getContext(), "Nothing to undo", Toast.LENGTH_LONG).show();

        }

    }

    public void normal() {
        emboss = false;
        blur = false;
    }

    public void emboss() {
        emboss=true;
        blur=false;
    }

    public void blur() {
        emboss=false;
        blur=true;
    }
    public void eraseon()
    {
            currentColor = backgroundColor;
            //mPaint.setXfermode(null);
            //mPaint.setAlpha(0xFF);
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            Toast.makeText(getContext(),"ERASOR ON",Toast.LENGTH_LONG).show();
    }
    public void eraseoff()
    {
        currentColor = lastColor;
        //mPaint.setXfermode(null);
        //mPaint.setShader(null);
        //mPaint.setMaskFilter(null);
        Toast.makeText(getContext(),"ERASOR OFF",Toast.LENGTH_LONG).show();
    }

    public void setBackground(int color)
    {
        backgroundColor = color;
        invalidate();
    }

    public void setStrokeWidth (int width) {

        strokeWidth = width;

    }

    public void setColor (int color) {

        currentColor = color;
        lastColor = currentColor;
    }

    @SuppressLint("WrongThread")
    public void saveImage () {

        int count = 0;

        File sdDirectory = Environment.getExternalStorageDirectory();
        File subDirectory = new File(sdDirectory.toString() + "/Pictures/Draw");

        if (subDirectory.exists()) {

            File[] existing = subDirectory.listFiles();

            for (File file : existing) {

                if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {

                    count++;

                }

            }

        } else {

            subDirectory.mkdir();

        }

        if (subDirectory.exists()) {

            File image = new File(subDirectory, "/drawing_" + (count + 1) + ".png");
            FileOutputStream fileOutputStream;

            try {

                fileOutputStream = new FileOutputStream(image);

                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

                fileOutputStream.flush();
                fileOutputStream.close();

                Toast.makeText(getContext(), "saved", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {


            } catch (IOException e) {


            }

        }

    }

}