package com.busanit.ex14_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location!=null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String message = "최근 위치 -> Latitude : "+latitude+"\nLongitude : "+longitude;
            textView.setText(message);
        }

        GPSListener listener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
        Toast.makeText(this,"내 위치 확인 요청함.",Toast.LENGTH_SHORT).show();
    }

    class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String message = "최근 위치 -> Latitude : "+latitude+"\nLongitude : "+longitude;
            textView.setText(message);
        }
    }
}