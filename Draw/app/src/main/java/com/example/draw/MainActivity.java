package com.example.draw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity
{
    private PaintView paintView;
    //private PaintView2 paintView;
    private int defaultColor;
    private int STORAGE_PERMISSION = 1;
    public Button colorButton;
    public Button bgButton;
    private int defaultBGcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintView = findViewById(R.id.paintView);
        colorButton = findViewById(R.id.Change_Color_Button);
        colorButton.setBackgroundColor(getResources().getColor(R.color.colorChange));

        DisplayMetrics displayMetrics = new DisplayMetrics();

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(10);

        final TextView textView = findViewById(R.id.Current_PenSize);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        paintView.initialise(displayMetrics);

        textView.setText("Pen Size:"+seekBar.getProgress());
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker();
                //colorButton.setBackgroundColor(defaultColor);
            }
        });
        colorButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "COLOR PALETTE!", Toast.LENGTH_LONG).show();
                return true;
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                paintView.setStrokeWidth(seekBar.getProgress());
                textView.setText("Pen Size:"+seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void requestStoragePermission () {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Needed to save image")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }

                    })
                    .create().show();

        } else {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Access granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "Access denied", Toast.LENGTH_LONG).show();

            }

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.blur:
                paintView.blur();
                return true;
            case R.id.emboss:
                paintView.emboss();
                return true;
            case R.id.erase:
                if(!item.isChecked())
                    paintView.eraseon();
                else
                    paintView.eraseoff();
                item.setChecked(!item.isChecked());
                return true;
            case R.id.background:
                openColourPicker2();
                return true;
            case R.id.clear_button:
                paintView.clear();
                return true;
            case R.id.undo_button:
                paintView.undo();
                return true;
            case R.id.redo_button:
                paintView.redo();
                return true;
            case R.id.save_button:

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestStoragePermission();

                }
                paintView.saveImage();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    private void openColourPicker () {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

                Toast.makeText(MainActivity.this, "COLOR NOT CHANGED!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                defaultColor = color;

                paintView.setColor(color);
                colorButton.setBackgroundColor(defaultColor);


            }

        });

        ambilWarnaDialog.show(); // add

    }
    private void openColourPicker2 () {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

                Toast.makeText(MainActivity.this, "COLOR NOT CHANGED!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                defaultBGcolor = color;

                paintView.setBackground(color);
                colorButton.setOutlineSpotShadowColor(color);
                colorButton.setShadowLayer(25,10,10,color);
            }

        });

        ambilWarnaDialog.show(); // add

    }
}