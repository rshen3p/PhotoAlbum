package ricky.chen.bcit.ca.cameraapp.Enlarge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import ricky.chen.bcit.ca.cameraapp.R;

public class EnlargeActivity extends AppCompatActivity {
    private ImageView picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge);

        picture = (ImageView)findViewById(R.id.picture);
        Intent intent = getIntent();

        byte[] bytes = intent.getByteArrayExtra("EXTRA_IMG");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        picture.setImageBitmap(bmp);
    }


}
