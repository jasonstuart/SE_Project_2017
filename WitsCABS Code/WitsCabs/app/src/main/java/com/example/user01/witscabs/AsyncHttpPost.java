package com.example.user01.witscabs;

import android.os.AsyncTask;
import android.os.AsyncTask;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class AsyncHttpPost extends AsyncTask {
    AsyncHandler p;
    List<NameValuePair> vars;
    String user;
    public AsyncHttpPost(List<NameValuePair> vars, com.example.user01.witscabs.AsyncHandler t) {
        this.vars = vars; this.p = t;}

    //original
    @Override
    protected String doInBackground(Object... params) {
        byte[] result = null;
        String str = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost((String) params[0]);// in this case, params[0] try {
        try {
             post.setEntity(new UrlEncodedFormEntity(vars));
            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
                result = EntityUtils.toByteArray(response.getEntity());
                str = new String(result, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return str;
    }

    /*    protected void onPostExecute(Object output) {
        // do something with the string returned earlier
                p.setText(output.toString());
                System.out.println(output);
            }*/
    protected void onPostExecute(Object output) {
        p.handleResponse((String) output);
    }
}