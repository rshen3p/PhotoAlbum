package ricky.chen.bcit.ca.cameraapp.Filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import ricky.chen.bcit.ca.cameraapp.Main.MainActivity;
import ricky.chen.bcit.ca.cameraapp.R;
import ricky.chen.bcit.ca.cameraapp.Filter.Result.ResultActivity;

public class FilterTextActivity extends AppCompatActivity {
    private ArrayList<File> files = new ArrayList<File>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<File> results = new ArrayList<File>();
    private EditText searchfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        files = (ArrayList<File>) getIntent().getSerializableExtra("DATA");

        for (int i = 0; i < files.size(); i++) {
            name.add(files.get(i).getName());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home(view);
            }
        });
    }

    public void search(View v) {
        searchfield = (EditText) findViewById(R.id.searchfiled);
        if (searchfield != null) {
            for (int i = 0; i < name.size(); i++) {
                if (name.get(i).contains(searchfield.getText())) {
                    results.add(files.get(i));
                }
            }

            Intent in = new Intent(this, ResultActivity.class);
            in.putExtra("pictures", results);
            startActivity(in);
        } else {
            Toast.makeText(this, "Please enter search term", Toast.LENGTH_SHORT).show();
        }
    }

    public void home(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
