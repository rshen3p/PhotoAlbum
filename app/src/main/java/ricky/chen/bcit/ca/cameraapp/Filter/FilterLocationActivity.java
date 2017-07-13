package ricky.chen.bcit.ca.cameraapp.Filter;

import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ricky.chen.bcit.ca.cameraapp.Main.MainActivity;
import ricky.chen.bcit.ca.cameraapp.R;
import ricky.chen.bcit.ca.cameraapp.Filter.Result.ResultActivity;

public class FilterLocationActivity extends AppCompatActivity {
    private ArrayList<File> files = new ArrayList<File>();
    private ArrayList<String> path = new ArrayList<String>();
    private ArrayList<File> results = new ArrayList<File>();
    private ExifInterface exif;
    private float[] latLong = new float[2];
    private boolean hasLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        files = (ArrayList<File>) getIntent().getSerializableExtra("DATA");

        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                path.add(files.get(i).getAbsolutePath());
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home(view);
            }
        });
    }

    public void search(View v) throws IOException {
        EditText lat = (EditText) findViewById(R.id.lat);
        EditText lon = (EditText) findViewById(R.id.log);

        String latFloat = lat.getText().toString();
        String lonFloat = lon.getText().toString();

        if (lat != null && lon != null) {
            for (int i = 0; i < path.size(); i++) {
                exif = new ExifInterface(files.get(i).getAbsolutePath());
                hasLatLong = exif.getLatLong(latLong);
                System.out.println(hasLatLong);

                if (latFloat.equalsIgnoreCase(String.valueOf(latLong[0])) && lonFloat.equalsIgnoreCase(String.valueOf(latLong[1]))) {
                    System.out.println(files.get(i).getAbsolutePath());
                    results.add(files.get(i));
                }
            }
        } else {
            Toast.makeText(this, "Please enter valid longitude and latitude data", Toast.LENGTH_SHORT).show();
        }

        Intent in = new Intent(this, ResultActivity.class);
        in.putExtra("pictures", results);
        startActivity(in);
    }

    public void home(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
