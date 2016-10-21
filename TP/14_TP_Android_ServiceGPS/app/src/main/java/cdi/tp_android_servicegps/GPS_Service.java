package cdi.tp_android_servicegps;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class GPS_Service extends Service {

    private LocationManager locatMgr = null;
    private LocationListener locatListener = null;

    public GPS_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("GPS_TAG", "onBind" + intent);
        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //super.onCreate();
        Toast.makeText(getBaseContext(), "GPS activé", Toast.LENGTH_SHORT).show();
        locatMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locatListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("GPS_TAG", "la localisation a changée");
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                Toast.makeText(getBaseContext(), "vos coordonnées : " + latitude + " " + longitude, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
               Log.d("GPS_TAG", "le status a changée");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("GPS_TAG", "le Provider est Enable");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("GPS_TAG", "le Provider est Disable");
            }
        };

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d("GPS_TAG", "Permission acordée");
            locatMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 0.0f, locatListener);
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        Log.d("GPS_TAG", "le service est détruit");
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locatMgr.removeUpdates(locatListener);
        }
    }
}
