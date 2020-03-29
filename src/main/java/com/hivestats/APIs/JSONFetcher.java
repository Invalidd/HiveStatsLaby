package com.hivestats.APIs;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JSONFetcher {

    public static String readUrl(String urlString) throws Exception {
        StringBuffer document = new StringBuffer();
        try
        {
            System.setProperty("http.agent", "");
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
                document.append(line + " ");
            reader.close();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return document.toString();
    }

    public static String getValueWithID(String data, String id){
        try {
            return new JsonParser().parse(data).getAsJsonObject().get(id).getAsString();
        }
        catch (Exception e){
            return null;
        }
    }
    public static JsonObject getValueObject(String data, String id){
        try {
            return new JsonParser().parse(data).getAsJsonObject().get(id).getAsJsonObject();
        }
        catch (Exception e){
            return null;
        }
    }
}
