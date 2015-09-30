package ommrepublic.com.ledieo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by admin on 9/23/15.
 */
public class BootReceiver extends BroadcastReceiver{
    static final String ACTION="android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Do something, Hello World?
        // Here is my code which will start my MainActivity
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //context.startService(new Intent(context, BootService.class));
    }
}
