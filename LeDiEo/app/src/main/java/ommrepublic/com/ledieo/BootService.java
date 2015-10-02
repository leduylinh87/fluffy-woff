package ommrepublic.com.ledieo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by admin on 9/24/15.
 */
public class BootService extends Service{

    private static String TAG = "BootService";

    private ServerSocket serverSocket;
    Handler updateConversationHandler;
    Thread serverThread = null;

    public static final int SERVERPORT = 6000;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.d(TAG, "FirstService started");
        //this.stopSelf();

        new RunTCPServerAsyncTask().execute();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "FirstService destroyed");
    }

    private class RunTCPServerAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params){
            // TODO Auto-generated method stub
            postData();

            return null;
        }

        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress){

        }

        public void postData() {

            Log.d("malware", "Run Server");
            ServerSocket ss = null;
            try {
                while(true) {
                    ss = new ServerSocket(SERVERPORT);
                    //ss.setSoTimeout(10000);
                    //accept connections
                    Socket s = ss.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    //receive a message
                    String incomingMsg = in.readLine() + System.getProperty("line.separator");
                    Log.i("TcpServer", "received: " + incomingMsg);
                    //textDisplay.append("received: " + incomingMsg);
                    //send a message
                    String outgoingMsg = "goodbye from port " + SERVERPORT + System.getProperty("line.separator");
                    out.write(outgoingMsg);
                    out.flush();
                    Log.i("TcpServer", "sent: " + outgoingMsg);
                    //textDisplay.append("sent: " + outgoingMsg);
                    //SystemClock.sleep(5000);
                    s.close();
                }
            } catch (Exception e) {
                //if timeout occurs
                e.printStackTrace();
            } finally {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


