package ricky.chen.bcit.ca.cameraapp.Filter.Result;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import ricky.chen.bcit.ca.cameraapp.Main.MainActivity;
import ricky.chen.bcit.ca.cameraapp.R;

public class ResultActivity extends AppCompatActivity {
    private ImageView result;
    private String path;
    private Bitmap image;
    private ArrayList<File> results;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        results = (ArrayList<File>)getIntent().getSerializableExtra("pictures");

        if(results != null && results.size() != 0) {
            path = results.get(0).getAbsolutePath();
            image = BitmapFactory.decodeFile(path);

            result = (ImageView) findViewById(R.id.resultView);
            result.setImageBitmap(image);
        } else {
            Toast.makeText(this,"No picture can be found",Toast.LENGTH_SHORT).show();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home(view);
            }
        });
    }

    public void home(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void left(View v){
        if(results != null && index > 0){
            index--;
            path = results.get(index).getAbsolutePath();
            image = BitmapFactory.decodeFile(path);

            result = (ImageView)findViewById(R.id.resultView);
            result.setImageBitmap(image);
        } else {
            Toast.makeText(this,"No more pictures ",Toast.LENGTH_SHORT).show();
        }
    }

    public void right(View v){
        if(results != null && index < results.size() - 1){
            index++;
            path = results.get(index).getAbsolutePath();
            image = BitmapFactory.decodeFile(path);

            result = (ImageView)findViewById(R.id.resultView);
            result.setImageBitmap(image);
        } else {
            Toast.makeText(this,"No more pictures",Toast.LENGTH_SHORT).show();
        }
    }

}
