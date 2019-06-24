package org.d3ifcool.dailyactivityroutine.News;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Fran on 26.02.2018.
 */

public class HtmlDownloader extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            return getWebSiteHtml(urls[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getWebSiteHtml(String webSite) throws Exception{
        URL url = new URL(webSite);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);

        int data = reader.read();
        String webSiteHtml = "";

        while (data != -1) {
            char current =(char) data;
            webSiteHtml += current;
            data = reader.read();
        }

        return webSiteHtml;
    }
}
