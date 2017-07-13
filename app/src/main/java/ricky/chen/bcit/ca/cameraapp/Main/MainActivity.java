package ricky.chen.bcit.ca.cameraapp.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ricky.chen.bcit.ca.cameraapp.Enlarge.EnlargeActivity;
import ricky.chen.bcit.ca.cameraapp.Filter.FilterActivity;
import ricky.chen.bcit.ca.cameraapp.R;

public class MainActivity extends AppCompatActivity {
    private ImageView thumbnail;
    static final int START_CAMERA_APP = 0;
    private String mImageLocation = "";
    private ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    private ArrayList<File> files = new ArrayList<File>();
    private int index = 0;
    public double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File("/storage/sdcard/Pictures/");
        File fa[] = file.listFiles();
        int count = 0;
        for (File f : fa) {
            String name = f.getName();
            if (name.endsWith(".jpg")) {
                count++;
                files.add(f);
            }
        }

        for (int i = 0; i < files.size(); i++) {
            Bitmap pic = BitmapFactory.decodeFile(files.get(i).getAbsolutePath());
            list.add(pic);
        }

        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        thumbnail.setImageBitmap(list.get(0));
    }

    public void takePhoto(View v) {
        index = 0;
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(callCameraApplicationIntent, START_CAMERA_APP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {
            Bitmap photoCaptured = BitmapFactory.decodeFile(mImageLocation);
            thumbnail.setImageBitmap(photoCaptured);
            list.add(photoCaptured);
        }
    }

    public void right(View v) {
        if (list != null && index < (list.size() - 1)) {
            index++;
            Bitmap photo = list.get(index);
            thumbnail.setImageBitmap(photo);
        } else {
            Toast.makeText(this, "No more pictures", Toast.LENGTH_SHORT).show();
        }
    }

    public void left(View v) {
        if (list != null && index > 0) {
            index--;
            Bitmap photo = list.get(index);
            thumbnail.setImageBitmap(photo);
        } else {
            Toast.makeText(this, "No more pictures", Toast.LENGTH_SHORT).show();
        }
    }

    File createImageFile() throws IOException, SecurityException {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyCurrentLoctionListener locationListener = new MyCurrentLoctionListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) locationListener);


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_" + lat + "_" + lon;
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageLocation = image.getAbsolutePath();

        files.add(image);
        return image;
    }

    public class MyCurrentLoctionListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();

            lat = location.getLatitude();
            lon = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    //use android jpeg header api
    public void enlarge(View v) {
        Intent enlargeIntent = new Intent(this, EnlargeActivity.class);
        Bitmap image = list.get(index);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        startActivity(enlargeIntent);
    }

    public void filter(View v) {
        Intent filterIntent = new Intent(this, FilterActivity.class);
        filterIntent.putExtra("DATA", files);
        startActivity(filterIntent);
    }

    public void upload(View v) {
        Toast.makeText(this, "Picture has been added to the upload queue", Toast.LENGTH_SHORT).show();
        try {
            wait(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, files.get(index).getName() + " has been uploaded", Toast.LENGTH_LONG).show();

    }


}
