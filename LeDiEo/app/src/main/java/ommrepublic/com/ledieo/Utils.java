package ommrepublic.com.ledieo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import ommrepublic.com.ledieo.Tor.AndroidOnionProxyManager;
import ommrepublic.com.ledieo.Tor.OnionProxyManager;
import ommrepublic.com.ledieo.Tor.Utilities;


/**
 * Created by admin on 9/24/15.
 */
public class Utils {

    public static void hideAppIcon(Context context)
    {
        PackageManager pkg = context.getPackageManager();
        pkg.setComponentEnabledSetting(new ComponentName(context, MainActivity.class),PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    public static void showAppIcon(Context context)
    {
        PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MainActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
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

    public static TextView torStatus;
    public static void setTorStatusText(String status)
    {
        torStatus.setText(status);
    }

    public static void startTor(Context context)
    {
        try {
            String fileStorageLocation = "torfiles";
            OnionProxyManager onionProxyManager = new AndroidOnionProxyManager(context.getApplicationContext(), fileStorageLocation);
            int totalSecondsPerTorStartup = 4 * 60;
            int totalTriesPerTorStartup = 5;

            // Start the Tor Onion Proxy
            if (onionProxyManager.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup) == false) {
                Toast.makeText(context, "Couldn't start tor", Toast.LENGTH_SHORT);
                Log.e("TorTest", "Couldn't start Tor!");
                return;
            }

            // Start a hidden service listener
            int hiddenServicePort = 80;
            int localPort = 9343;
            String onionAddress = onionProxyManager.publishHiddenService(hiddenServicePort, localPort);

            // It can taken anywhere from 30 seconds to a few minutes for Tor to start properly routing
            // requests to to a hidden service. So you generally want to try to test connect to it a
            // few times. But after the previous call the Tor Onion Proxy will route any requests
            // to the returned onionAddress and hiddenServicePort to 127.0.0.1:localPort. So, for example,
            // you could just pass localPort into the NanoHTTPD constructor and have a HTTP server listening
            // to that port.

            // Connect via the TOR network
            // In this case we are trying to connect to the hidden service but any IP/DNS address and port can be
            // used here.
            Socket clientSocket = Utilities.socks4aSocketConnection(onionAddress, hiddenServicePort, "127.0.0.1", localPort);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Utils.setTorStatusText("OK");
        return;
    }
}
