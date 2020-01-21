package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private TextView txtResult;
    private ImageButton ImgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 오늘 날짜
        // 깃 테스트
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String nowDate = sdf.format(new Date());
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(nowDate);

        txtResult = (TextView)findViewById(R.id.textview_address);
        ImgButton = (ImageButton)findViewById(R.id.imageButton);

        final LocationManager Lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        ImgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
                } else {
                    Location location = Lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();

                    txtResult.setText("위치정보 : "+provider+"\n"+
                            "위도 : "+longitude+"\n"+
                            "경도 : "+latitude+"\n"+
                            "고도 : "+altitude);
                    Lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                    Lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
                }
            }
        });
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            txtResult.setText("위치정보 : "+provider+"\n"+
                    "위도 : "+longitude+"\n"+
                    "경도 : "+latitude+"\n"+
                    "고도 : "+altitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
