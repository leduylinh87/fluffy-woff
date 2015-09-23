package ommrepublic.com.ledieo;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * Created by admin on 9/23/15.
 */
public class DeviceLocation {

    public boolean IsWiFiAvailable(Context a){
        ConnectivityManager connectivity = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) return false; // as of the API, this can only happen if the name does not exist, but we try to evade even this

        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo(); // requires ACCESS_NETWORK_STATE
        if (activeNetwork.isConnected() && activeNetwork.getType()==ConnectivityManager.TYPE_WIFI) {
            return true; // return true only if WiFi and active
        }
        return false;
    }

    public double[] getGPS(Context a) {
        try {
            LocationManager lm = (LocationManager) a.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = lm.getProviders(true);

            /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
            Location l = null;

            for (int i=providers.size()-1; i>=0; i--) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) break;
            }

            double[] gps = new double[2];
            if (l != null) {
                gps[0] = l.getLatitude();
                gps[1] = l.getLongitude();
            }
            return gps;
        }
        catch (Exception e) {
            // I don't even know how this might happen
            return null;
        }
    }
}
