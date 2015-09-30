package ommrepublic.com.ledieo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by admin on 9/24/15.
 */
public class Utils {

    public static void hideAppIcon(Context context)
    {
        PackageManager p = context.getPackageManager();
        p.setComponentEnabledSetting(((Activity)context).getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);


        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }


        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }


        is.close();
        return bytes;
    }
}
