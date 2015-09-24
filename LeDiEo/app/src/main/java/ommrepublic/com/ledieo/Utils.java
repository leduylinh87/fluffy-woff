package ommrepublic.com.ledieo;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;


/**
 * Created by admin on 9/24/15.
 */
public class Utils {

    public static void hideAppIcon(Context context)
    {
        PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
